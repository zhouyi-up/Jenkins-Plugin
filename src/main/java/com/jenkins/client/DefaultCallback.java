package com.jenkins.client;

import com.intellij.icons.AllIcons;
import com.intellij.notification.Notification;
import com.intellij.notification.NotificationType;
import com.intellij.notification.Notifications;
import com.jenkins.compent.JenkinsNotificationComponent;
import com.jenkins.utils.JsonUtils;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;
import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.lang.reflect.ParameterizedType;

/**
 * @author corele
 */
public abstract class DefaultCallback<T> implements Callback {

    private Response response;

    @Override
    public void onFailure(@NotNull Call call, @NotNull IOException e) {
        JenkinsNotificationComponent.notifyError(null, "The Network is Error, Please check the network");
        error(e);
    }

    @Override
    public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
        this.response = response;
        if (response.isSuccessful()){
            String bodyString = response.body().string();
            if (StringUtils.isEmpty(bodyString)){
                success(null);
                return;
            }
            T data = JsonUtils.parseObject(bodyString, getTClass());
            success(data);
        }else {
            buildError(response.code());
            error(new RuntimeException(response.toString()));
        }
    }

    public Class<T> getTClass() {
        Class<T> tClass = (Class<T>)((ParameterizedType)getClass().getGenericSuperclass()).getActualTypeArguments()[0];
        return tClass;
    }

    /**
     * 请求成功
     * @param data
     */
    public abstract void success(T data);


    /**
     * 错误
     * @param exception
     */
    public void error(Exception exception){
    }


    private void buildError(int httpStatus){
        if (httpStatus == 401){
            JenkinsNotificationComponent.notifyWarning(null, "Jenkins Auth Error","Please check your config for jenkins.");
        }else {
            JenkinsNotificationComponent.notifyError(null, "Jenkins Plugin",response.body().toString());
        }
    }
}
