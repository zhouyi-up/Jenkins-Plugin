package com.jenkins.client;

import com.jenkins.compent.JenkinsNotificationComponent;
import com.jenkins.utils.JsonUtils;
import okhttp3.Response;
import okhttp3.ResponseBody;
import org.apache.commons.lang3.StringUtils;

/**
 * @author liujun
 */
public final class ResponseUtils {

    public static <T> T getClass(Class<T> clazz, ResponseBody body){
        String json = body.toString();
        if (StringUtils.isEmpty(json)){
            return null;
        }
        T t = JsonUtils.parseObject(json, clazz);
        return t;
    }

    public static void notifyError(Response response){
        int httpStatus = response.code();
        if (httpStatus == 401){
            JenkinsNotificationComponent.notifyWarning(null, "Jenkins Auth Error","Please check your config for jenkins.");
        }else {
            JenkinsNotificationComponent.notifyError(null, "Jenkins Plugin",response.body().toString());
        }
    }
}
