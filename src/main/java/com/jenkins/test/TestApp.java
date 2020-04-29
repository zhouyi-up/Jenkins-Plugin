package com.jenkins.test;

import com.jenkins.utils.JsonUtils;
import com.jenkins.client.DefaultCallback;
import com.jenkins.client.JenkinsClientAsync;
import com.jenkins.model.JobListEntity;

import java.io.IOException;

/**
 * @author corele
 */
public class TestApp {
    public static void main(String[] args) throws IOException {
        String url = "http://106.12.88.29:8000/";
        JenkinsClientAsync jenkinsClientAsync = new JenkinsClientAsync(url, "admin", "Corele1024.");

        jenkinsClientAsync.build("Test-Job", new DefaultCallback<String>() {
            @Override
            public void success(String data) {
//                System.out.println(JsonUtils.toJsonString(data));
            }

            @Override
            public void error(Exception exception) {
//                System.out.println(exception);
            }
        });
    }
}
