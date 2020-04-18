package com.jenkins;

import com.intellij.icons.AllIcons;
import com.intellij.notification.Notification;
import com.intellij.notification.NotificationType;
import com.intellij.notification.Notifications;
import com.jenkins.model.JobEntity;
import com.jenkins.model.JobListEntity;
import org.apache.commons.io.IOUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpStatus;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.Map;

/**
 * @author liujun
 */
public class JenkinsClient {

    private String jenkinsHost;
    private String username;
    private String password;

    private JenkinsClient(){};

    public JenkinsClient(String jenkinsHost,String username,String password){
        this.jenkinsHost = jenkinsHost;
        this.username = username;
        this.password = password;
    }

    private CloseableHttpClient httpClient;

    public Crumb getCrumb(CloseableHttpClient httpClient){
        String url =  jenkinsHost + "crumbIssuer/api/json";

        HttpGet httpGet = new HttpGet(url);
        httpGet.addHeader("Authorization",auth());

        try {
            CloseableHttpResponse response = httpClient.execute(httpGet);
            int statusCode = response.getStatusLine().getStatusCode();
            if (statusCode == HttpStatus.SC_OK || statusCode == HttpStatus.SC_CREATED){
                HttpEntity entity = response.getEntity();
                InputStream content = entity.getContent();
                String body = IOUtils.toString(content, "UTF-8");
                Crumb jobListEntity = JsonUtils.parseObject(body, Crumb.class);
                return jobListEntity;
            }else {
                Notification notification = new Notification("",
                        AllIcons.Actions.Forward, NotificationType.WARNING);
                notification.setContent("The auth for request is error!");
                notification.setTitle("HTTP "+statusCode);
                Notifications.Bus.notify(notification);
                return new Crumb();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public JenkinsResponse<Object> buildNoParam(BuildParam buildParam){
        CloseableHttpClient httpClient = buildHttpClient();

        String url = jenkinsHost+"/job/"+buildParam.getJobName()+"/build";
        HttpPost httpPost = new HttpPost(url);
        httpPost.addHeader("Authorization",auth());

        Crumb crumb = getCrumb(httpClient);
        httpPost.addHeader(crumb.getCrumbRequestField(),crumb.getCrumb());
        try {
            CloseableHttpResponse response = httpClient.execute(httpPost);
            int statusCode = response.getStatusLine().getStatusCode();
            if (statusCode == HttpStatus.SC_OK || statusCode == HttpStatus.SC_CREATED){
                JenkinsResponse<Object> jenkinsResponse = new JenkinsResponse<>();
                jenkinsResponse.setErrorCode(JenkinsResponse.SUCCESS);
                jenkinsResponse.setErrorMsg("ok");
                return jenkinsResponse;
            }else {
                JenkinsResponse<Object> jenkinsResponse = new JenkinsResponse<>();
                jenkinsResponse.setErrorCode(JenkinsResponse.FAIL);
                HttpEntity entity = response.getEntity();
                InputStream content = entity.getContent();
                String body = IOUtils.toString(content, "UTF-8");
                jenkinsResponse.setErrorMsg(body);
                return jenkinsResponse;
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    public JenkinsResponse buildParam(BuildParam buildParam){
        CloseableHttpClient httpClient = buildHttpClient();

        Crumb crumb = getCrumb(httpClient);

        String url = jenkinsHost+"/job/"+buildParam.getJobName()+"/buildWithParameters";
        HttpPost httpPost = new HttpPost(url);
        httpPost.addHeader("Authorization",auth());
        httpPost.addHeader("Content-type","multipart/form-data");
        httpPost.addHeader("Accept","*/*");
        httpPost.addHeader("Connection","keep-alive");
        httpPost.addHeader(crumb.getCrumbRequestField(),crumb.getCrumb());
        try {
            if (!buildParam.getParam().isEmpty()){
                List<BasicNameValuePair> paramList = new ArrayList<>();
                buildParam.getParam().keySet().forEach(e -> {
                    paramList.add(new BasicNameValuePair(e,buildParam.getParam().get(e)));
                });
                HttpEntity httpEntity = new UrlEncodedFormEntity(paramList,"UTF-8");
                httpPost.setEntity(httpEntity);
            }
            CloseableHttpResponse response = httpClient.execute(httpPost);
            int statusCode = response.getStatusLine().getStatusCode();
            if (statusCode == HttpStatus.SC_OK || statusCode == HttpStatus.SC_CREATED){
                JenkinsResponse<Object> jenkinsResponse = new JenkinsResponse<>();
                jenkinsResponse.setErrorCode(JenkinsResponse.SUCCESS);
                jenkinsResponse.setErrorMsg("ok");
                return jenkinsResponse;
            }else {
                JenkinsResponse<Object> jenkinsResponse = new JenkinsResponse<>();
                jenkinsResponse.setErrorCode(JenkinsResponse.FAIL);
                HttpEntity entity = response.getEntity();
                InputStream content = entity.getContent();
                String body = IOUtils.toString(content, "UTF-8");
                jenkinsResponse.setErrorMsg(body);
                return jenkinsResponse;
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public JenkinsResponse<JobEntity> queryJobInfo(String jobName){
        CloseableHttpClient httpClient = buildHttpClient();

        String url = jenkinsHost + "/job/"+jobName+"/api/json";

        HttpGet httpGet = new HttpGet(url);
        httpGet.addHeader("Authorization",auth());

        Crumb crumb = getCrumb(httpClient);
        httpGet.addHeader(crumb.getCrumbRequestField(),crumb.getCrumb());

        try {
            CloseableHttpResponse response = httpClient.execute(httpGet);
            int statusCode = response.getStatusLine().getStatusCode();
            if (statusCode == HttpStatus.SC_OK || statusCode == HttpStatus.SC_CREATED){
                JenkinsResponse<JobEntity> jenkinsResponse = new JenkinsResponse<>();
                jenkinsResponse.setErrorCode(JenkinsResponse.SUCCESS);
                jenkinsResponse.setErrorMsg("ok");
                HttpEntity entity = response.getEntity();
                InputStream content = entity.getContent();
                String body = IOUtils.toString(content, "UTF-8");
                JobEntity jobEntity = JsonUtils.parseObject(body, JobEntity.class);
                jenkinsResponse.setBody(jobEntity);
                return jenkinsResponse;
            }else {
                JenkinsResponse<JobEntity> jenkinsResponse = new JenkinsResponse<>();
                jenkinsResponse.setErrorCode(JenkinsResponse.FAIL);
                HttpEntity entity = response.getEntity();
                InputStream content = entity.getContent();
                String body = IOUtils.toString(content, "UTF-8");
                jenkinsResponse.setErrorMsg(body);
                return jenkinsResponse;
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }finally {
            try {
                httpClient.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            ;
        }
    }

    public JenkinsResponse<JobListEntity> queryAllJob(){
        CloseableHttpClient httpClient = buildHttpClient();

        String url = jenkinsHost + "/api/json";

        HttpGet httpGet = new HttpGet(url);
        httpGet.addHeader("Authorization",auth());
        Crumb crumb = getCrumb(httpClient);
        httpGet.addHeader(crumb.getCrumbRequestField(),crumb.getCrumb());

        try {
            CloseableHttpResponse response = httpClient.execute(httpGet);
            int statusCode = response.getStatusLine().getStatusCode();
            if (statusCode == HttpStatus.SC_OK || statusCode == HttpStatus.SC_CREATED){
                JenkinsResponse<JobListEntity> jenkinsResponse = new JenkinsResponse<>();
                jenkinsResponse.setErrorCode(JenkinsResponse.SUCCESS);
                jenkinsResponse.setErrorMsg("ok");
                HttpEntity entity = response.getEntity();
                InputStream content = entity.getContent();
                String body = IOUtils.toString(content, "UTF-8");
                JobListEntity jobListEntity = JsonUtils.parseObject(body, JobListEntity.class);
                jenkinsResponse.setBody(jobListEntity);
                return jenkinsResponse;
            }else {
                JenkinsResponse<JobListEntity> jenkinsResponse = new JenkinsResponse<>();
                jenkinsResponse.setErrorCode(JenkinsResponse.FAIL);
                HttpEntity entity = response.getEntity();
                InputStream content = entity.getContent();
                String body = IOUtils.toString(content, "UTF-8");
                jenkinsResponse.setErrorMsg(body);
                return jenkinsResponse;
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private String auth() {
        return "Basic "+ Base64.getUrlEncoder().encodeToString((username+":"+password).getBytes());
    }

    private CloseableHttpClient buildHttpClient() {
        if(httpClient == null){
            httpClient =  HttpClientBuilder.create().build();
        }
        return HttpClientBuilder.create().build();
    }

    public static class Crumb{


        private String _class;
        private String crumb;
        private String crumbRequestField;

        public String get_class() {
            return _class;
        }

        public void set_class(String _class) {
            this._class = _class;
        }

        public String getCrumb() {
            return crumb;
        }

        public void setCrumb(String crumb) {
            this.crumb = crumb;
        }

        public String getCrumbRequestField() {
            return crumbRequestField;
        }

        public void setCrumbRequestField(String crumbRequestField) {
            this.crumbRequestField = crumbRequestField;
        }
    }
}
