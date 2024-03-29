package com.jenkins.client;

import com.google.common.collect.Maps;
import com.jenkins.compent.JenkinsSettingDataComponent;
import com.jenkins.model.JobBuildInfo;
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

    private OkHttpClient client = null;
    JenkinsSettingDataComponent settingComponent;

    public JenkinsClientAsync(){

        settingComponent = JenkinsSettingDataComponent.getInstance();

        client = new OkHttpClient.Builder()
                .addInterceptor(getHttpLoggingInterceptor())
                .addInterceptor(JenkinsAuthInterceptor.getInstance(settingComponent))
                .cookieJar(JenkinsCookieJar.getInstance())
                .build();
    }

    @NotNull
    private HttpLoggingInterceptor getHttpLoggingInterceptor() {
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor(new HttpLogger());
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        return loggingInterceptor;
    }

    /**
     * job 列表
     * @param callback
     */
    public void jobList(DefaultCallback<JobListEntity> callback){
        String url = settingComponent.getHost() + "/api/json";

        Request request = new Request.Builder()
                .url(url)
                .build();
        client.newCall(request).enqueue(callback);
    }

    /**
     * job 列表
     * @param callback
     */
    public void jobList(String viewName, DefaultCallback<JobListEntity> callback){
        String url = settingComponent.getHost() +"/view/"+viewName + "/api/json";

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
        String url = settingComponent.getHost() + "/job/"+jobName+"/api/json";
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
        String url = settingComponent.getHost() +"/job/"+buildParam.getJobName()+"/buildWithParameters";

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
        String url = settingComponent.getHost() +"/job/"+jobName+"/build";
        HashMap<String, Object> json = Maps.newHashMap();

        Request request = new Request.Builder()
                .addHeader("Content-Type", "application/x-www-form-urlencoded")
                .url(url)
                .build();

        client.newCall(request).enqueue(callback);
    }

    public JobBuildInfo buildInfo(String jobName, int number) throws IOException {
        String url = settingComponent.getHost() + "/job/" + jobName + "/" + number + "/api/json";
        Request request = new Request.Builder()
                .url(url)
                .build();
        Response response = client.newCall(request).execute();
        if (response.isSuccessful()){
            return ResponseUtils.getClass(JobBuildInfo.class, response.body());
        }else {
            ResponseUtils.notifyError(response);
            return null;
        }
    }
}
