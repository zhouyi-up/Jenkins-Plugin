package com.jenkins.client;

import com.jenkins.compent.JenkinsNotificationComponent;
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

    private final String username;
    private final String password;
    private final String jenkinsHost;

    private boolean enableCrumb;
    private Crumb crumb;

    private JenkinsAuthInterceptor(String jenkinsHost, String username, String password, boolean enableCrumb) {
        this.jenkinsHost = jenkinsHost;
        this.username = username;
        this.password = password;
        this.enableCrumb = enableCrumb;
    }

    public static JenkinsAuthInterceptor getInstance(String jenkinsHost, String username, String password, boolean enableCrumb){
        if (jenkinsAuthInterceptor == null){
            jenkinsAuthInterceptor = new JenkinsAuthInterceptor(jenkinsHost,username,password,enableCrumb);
        }
        return jenkinsAuthInterceptor;
    }


    @NotNull
    @Override
    public Response intercept(@NotNull Interceptor.Chain chain) throws IOException {
        Request request = chain.request();
        Request.Builder authorizationBuilder = request.newBuilder()
                .addHeader("Authorization", AuthUtils.auth(username, password));
        if (enableCrumb){
            if (checkCrumbNull()){
                crumb = getCrumb();
            }
            if (crumb != null){
                authorizationBuilder.addHeader(crumb.getCrumbRequestField(),crumb.getCrumb());
            }
        }
        return chain.proceed(authorizationBuilder.build());
    }


    private boolean checkCrumbNull() {
        return crumb == null || StringUtils.isEmpty(crumb.getCrumbRequestField()) || StringUtils.isEmpty(crumb.getCrumb());
    }

    private Crumb getCrumb() throws IOException {
        String url =  jenkinsHost + "/crumbIssuer/api/json";
        Request request = new Request.Builder()
                .url(url)
                .addHeader("Authorization",AuthUtils.auth(username,password))
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
            JenkinsNotificationComponent.notifyError(null,"Crumb Build Error", response.toString());
            return null;
        }
    }
}
