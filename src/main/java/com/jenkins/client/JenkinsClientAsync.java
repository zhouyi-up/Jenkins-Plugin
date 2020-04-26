package com.jenkins.client;

import com.google.common.collect.Lists;
import com.jenkins.config.HttpLogger;
import com.jenkins.utils.JsonUtils;
import com.jenkins.model.JobEntity;
import com.jenkins.model.JobListEntity;
import okhttp3.*;
import okhttp3.logging.HttpLoggingInterceptor;

import java.io.IOException;
import java.util.Base64;
import java.util.List;

/**
 * @author corele
 */
public class JenkinsClientAsync {

    private String username = "admin";
    private String password = "admin";

    private String jenkinsHost = "http://127.0.0.1:8080/";

    private OkHttpClient client = null;

    private Crumb crumb = null;

    public JenkinsClientAsync(String jenkinsHost,String username,String password){
        this.username = username;
        this.password = password;
        this.jenkinsHost = jenkinsHost;

        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor(new HttpLogger());
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        client = new OkHttpClient.Builder()
                .addInterceptor(loggingInterceptor)
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
                .build();
        Response response = client.newCall(request).execute();
        return JsonUtils.parseObject(response.body().string(),Crumb.class);
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
                .addHeader("Authorization",auth())
                .addHeader(crumb.getCrumbRequestField(),crumb.getCrumb())
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
                .addHeader("Authorization",auth())
                .addHeader(crumb.getCrumbRequestField(),crumb.getCrumb())
                .build();
        client.newCall(request).enqueue(callback);
    }

    /**
     * 构建
     * @param buildParam
     * @param callback
     */
    public void build(BuildParam buildParam, DefaultCallback<String> callback){
        String url = jenkinsHost+"job/"+buildParam.getJobName()+"/build";

        if (buildParam.getParam().isEmpty() ||
            buildParam.getParam().size() == 0
        ){
            return;
        }

        MultipartBody.Builder builder = new MultipartBody.Builder();
        for (String key : buildParam.getParam().keySet()) {
            builder.addFormDataPart(key,buildParam.getParam().get(key));
        }

        RequestBody requestBody = builder.build();


        Request request = new Request.Builder()
                .url(url)
                .post(requestBody)
                .addHeader("Content-Type","multipart/form-data")
                .addHeader("Authorization",auth())
                .addHeader(crumb.getCrumbRequestField(),crumb.getCrumb())
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

        RequestBody requestBody = new FormBody(Lists.newArrayList(),Lists.newArrayList());

        Request request = new Request.Builder()
                .post(requestBody)
                .url(url)
                .addHeader("Content-Type","multipart/form-data")
                .addHeader("Authorization",auth())
                .addHeader(crumb.getCrumbRequestField(),crumb.getCrumb())
                .build();

        client.newCall(request).enqueue(callback);
    }
}
