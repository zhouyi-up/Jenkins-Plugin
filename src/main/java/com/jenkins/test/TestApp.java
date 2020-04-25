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
        String url = "http://hw.corele.top:8000/";
        JenkinsClientAsync jenkinsClientAsync = new JenkinsClientAsync(url, "admin", "Corele1024.");

        jenkinsClientAsync.jobList(new DefaultCallback<JobListEntity>() {
            @Override
            public void success(JobListEntity data) {
                System.out.println(JsonUtils.toJsonString(data));
            }
        });
    }
}
