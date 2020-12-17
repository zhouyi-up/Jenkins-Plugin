package com.jenkins.client;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.intellij.icons.AllIcons;
import com.intellij.notification.Notification;
import com.intellij.notification.NotificationType;
import com.intellij.notification.Notifications;
import com.jenkins.config.HttpLogger;
import com.jenkins.utils.JsonUtils;
import com.jenkins.model.JobEntity;
import com.jenkins.model.JobListEntity;
import okhttp3.*;
import okhttp3.logging.HttpLoggingInterceptor;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.*;

/**
 * @author corele
 */
public class JenkinsClientAsync {

    private String username = "admin";
    private String password = "admin";

    private String jenkinsHost = "http://127.0.0.1:8080/";

    private OkHttpClient client = null;

    private Crumb crumb = null;

    private boolean enableCrumb;

    private final HashMap<String, List<Cookie>> cookieStore = new HashMap<>();

    public JenkinsClientAsync(String jenkinsHost,String username,String password, boolean enableCrumb){
        this.username = username;
        this.password = password;
        this.jenkinsHost = jenkinsHost;
        this.enableCrumb = enableCrumb;

        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor(new HttpLogger());
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        client = new OkHttpClient.Builder()
                .addInterceptor(loggingInterceptor)
                .addInterceptor(new Interceptor() {
                    @NotNull
                    @Override
                    public Response intercept(@NotNull Chain chain) throws IOException {
                        Request request = chain.request();
                        Request.Builder authorizationBuilder = request.newBuilder()
                                .addHeader("Authorization", auth());
                        if (enableCrumb){
                            if (crumb == null){
                                crumb = getCrumb();
                            }
                            authorizationBuilder.addHeader(crumb.getCrumbRequestField(),crumb.getCrumb());
                        }
                        return chain.proceed(authorizationBuilder.build());
                    }
                })
                .cookieJar(new CookieJar() {
                    @Override
                    public void saveFromResponse(@NotNull HttpUrl httpUrl, @NotNull List<Cookie> list) {
                        if (list == null){
                            return;
                        }
                        cookieStore.put(httpUrl.host(), list);
                    }

                    @NotNull
                    @Override
                    public List<Cookie> loadForRequest(@NotNull HttpUrl httpUrl) {
                        List<Cookie> cookies = cookieStore.get(httpUrl.host());
                        return cookies != null ? cookies : new ArrayList<Cookie>();
                    }
                })
                .build();
        refreshCrumb();
    }

    public void refreshCrumb(){
        try {
            crumb = getCrumb();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private Crumb getCrumb() throws IOException {
        String url =  jenkinsHost + "crumbIssuer/api/json";
        Request request = new Request.Builder()
                .url(url)
                .addHeader("Authorization",auth())
                .addHeader("Connection","keep-alive")
                .build();
        Response response = new OkHttpClient.Builder()
                .cookieJar(new CookieJar() {
                    @Override
                    public void saveFromResponse(@NotNull HttpUrl httpUrl, @NotNull List<Cookie> list) {
                        if (list == null){
                            return;
                        }
                        cookieStore.put(httpUrl.host(), list);
                    }

                    @NotNull
                    @Override
                    public List<Cookie> loadForRequest(@NotNull HttpUrl httpUrl) {
                        List<Cookie> cookies = cookieStore.get(httpUrl.host());
                        return cookies != null ? cookies : new ArrayList<Cookie>();
                    }
                })
                .build()
                .newCall(request)
                .execute();
        if (response.isSuccessful()){
            return JsonUtils.parseObject(response.body().string(),Crumb.class);
        }else {
            Notification notification = new Notification("jenkins.ui.id",
                    AllIcons.Actions.Forward, NotificationType.WARNING);
            notification.setContent(response.toString());
            notification.setTitle("Build Error ");
            Notifications.Bus.notify(notification);
            return new Crumb();
        }
    }

    private String auth() {
        return "Basic "+ Base64.getUrlEncoder().encodeToString((username+":"+password).getBytes());
    }

    /**
     * job 列表
     * @param callback
     */
    public void jobList(DefaultCallback<JobListEntity> callback){
        String url = jenkinsHost + "api/json";

        Request request = new Request.Builder()
                .url(url)
                .build();
        client.newCall(request).enqueue(callback);
    }

    /**
     * job详情
     * @param jobName
     * @param callback
     */
    public void jobInfo(String jobName, DefaultCallback<JobEntity> callback){
        String url = jenkinsHost + "/job/"+jobName+"/api/json";
        Request request = new Request.Builder()
                .url(url)
                .build();
        client.newCall(request).enqueue(callback);
    }

    /**
     * 构建
     * @param buildParam
     * @param callback
     */
    public void build(BuildParam buildParam, DefaultCallback<String> callback){
        String url = jenkinsHost+"/job/"+buildParam.getJobName()+"/buildWithParameters";

        if (buildParam.getParam().isEmpty() ||
            buildParam.getParam().size() == 0
        ){
            return;
        }
        FormBody.Builder builder = new FormBody.Builder();
        for (String key : buildParam.getParam().keySet()) {
            builder.add(key,buildParam.getParam().get(key));
        }
        RequestBody requestBody = builder.build();
        Request request = new Request.Builder()
                .url(url)
                .post(requestBody)
                .addHeader("Content-Type","multipart/form-data")
                .build();

        client.newCall(request).enqueue(callback);
    }

    /**
     * 构建
     * @param jobName
     * @param callback
     */
    public void build(String jobName,DefaultCallback<String> callback){
        String url = jenkinsHost+"job/"+jobName+"/build";
        HashMap<String, Object> json = Maps.newHashMap();
        json.put(crumb.getCrumbRequestField(), crumb.getCrumb());

        RequestBody requestBody = new FormBody(Lists.newArrayList(crumb.getCrumbRequestField(), "json"),
                Lists.newArrayList(crumb.getCrumb(), JsonUtils.toJsonString(json)));

        Request request = new Request.Builder()
                .addHeader("Content-Type", "application/x-www-form-urlencoded")
                .post(requestBody)
                .url(url)
                .build();

        client.newCall(request).enqueue(callback);
    }
}
