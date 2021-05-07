package com.jenkins.client;

import com.jenkins.compent.JenkinsComponent;
import com.jenkins.compent.JenkinsNotificationComponent;
import com.jenkins.compent.JenkinsSettingDataComponent;
import com.jenkins.utils.JsonUtils;
import okhttp3.*;
import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author liujun
 */
public class JenkinsAuthInterceptor implements Interceptor {

    private static JenkinsAuthInterceptor jenkinsAuthInterceptor;

    private final JenkinsSettingDataComponent settingComponent;

    private boolean enableCrumb;
    private Crumb crumb;

    private JenkinsAuthInterceptor(JenkinsSettingDataComponent settingComponent) {
        this.settingComponent = settingComponent;
    }

    public static JenkinsAuthInterceptor getInstance(JenkinsSettingDataComponent settingComponent){
        if (jenkinsAuthInterceptor == null){
            jenkinsAuthInterceptor = new JenkinsAuthInterceptor(settingComponent);
        }
        return jenkinsAuthInterceptor;
    }


    @NotNull
    @Override
    public Response intercept(@NotNull Interceptor.Chain chain) throws IOException {
        Request request = chain.request();
        Request.Builder authorizationBuilder = request.newBuilder()
                .addHeader("Authorization", AuthUtils.auth(settingComponent.getUsername(), settingComponent.getPassword()));
        if (enableCrumb){
            if (checkCrumbNull()){
                crumb = getCrumb();
            }
            if (crumb != null){
                authorizationBuilder.addHeader(crumb.getCrumbRequestField(),crumb.getCrumb());
            }
        }
        Request bu = authorizationBuilder.build();
        return chain.proceed(bu);
    }


    private boolean checkCrumbNull() {
        return crumb == null || StringUtils.isEmpty(crumb.getCrumbRequestField()) || StringUtils.isEmpty(crumb.getCrumb());
    }

    private Crumb getCrumb() throws IOException {
        String url =  settingComponent.getHost() + "/crumbIssuer/api/json";
        Request request = new Request.Builder()
                .url(url)
                .addHeader("Authorization",AuthUtils.auth(settingComponent.getUsername(),settingComponent.getPassword()))
                .addHeader("Connection","keep-alive")
                .build();
        Response response = new OkHttpClient.Builder()
                .cookieJar(JenkinsCookieJar.getInstance())
                .build()
                .newCall(request)
                .execute();
        if (response.isSuccessful()){
            String string = response.body().string();
            response.close();
            return JsonUtils.parseObject(string,Crumb.class);
        }else {
            response.close();
            JenkinsNotificationComponent.notifyWarning(null,"Crumb build Error","You can try shutting down the crumb for jenkins tools config");
            return null;
        }
    }
}
