package com.jenkins.compent;

import com.intellij.openapi.progress.ProgressIndicator;
import com.intellij.openapi.progress.ProgressManager;
import com.intellij.openapi.progress.Task;
import com.intellij.openapi.project.Project;
import com.jenkins.client.*;
import com.jenkins.model.JobBuildInfo;
import com.jenkins.model.JobEntity;
import com.jenkins.model.JobListEntity;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;

/**
 * @author liujun
 */
public class JenkinsComponent {

    private static JenkinsComponent jenkinsComponent;

    private JenkinsClientAsync jenkinsClientAsync;
    private final JenkinsSettingDataComponent settingData;
    private final Project project;

    private JenkinsComponent(Project project){
        this.project = project;
        settingData = JenkinsSettingDataComponent.getInstance();
        initJenkinsClient();
    }

    /**
     * inited
     */
    private void initJenkinsClient() {
        jenkinsClientAsync = new JenkinsClientAsync(settingData.getHost(),
                settingData.getUsername(), settingData.getPassword(),
                settingData.getEnableCrumb());
    }

    /**
     * get instance
     * @param project project
     * @return this
     */
    public static JenkinsComponent getInstance(Project project){
        if (jenkinsComponent == null){
            jenkinsComponent = new JenkinsComponent(project);
        }
        return jenkinsComponent;
    }

    public void refreshClient(){
        initJenkinsClient();
    }

    public void build(String jobName, JenkinsSuccess<String> jenkinsSuccess, JenkinsError jenkinsError){
        run(() -> {
            jenkinsClientAsync.build(jobName, getBuildCallback(jenkinsSuccess, jenkinsError));
        });
    }

    @NotNull
    private DefaultCallback<String> getBuildCallback(JenkinsSuccess<String> jenkinsSuccess, JenkinsError jenkinsError) {
        return new DefaultCallback<>() {
            @Override
            public void success(String data) {
                jenkinsSuccess.success(data);
            }

            @Override
            public void error(Exception exception) {
                super.error(exception);
                jenkinsError.error();
            }
        };
    }

    public void build(BuildParam buildParam, JenkinsSuccess<String> jenkinsSuccess, JenkinsError jenkinsError){
        run(() -> {
            jenkinsClientAsync.build(buildParam, getBuildCallback(jenkinsSuccess, jenkinsError));
        });
    }

    public void jobInfo(String jobName, JenkinsSuccess<JobEntity> success, JenkinsError error){
        run(() -> {
            jenkinsClientAsync.jobInfo(jobName, new DefaultCallback<JobEntity>() {
                @Override
                public void success(JobEntity data) {
                    success.success(data);
                }
            });
        });
    }

    public void jobList(JenkinsSuccess<JobListEntity> success, JenkinsError error){
        run(() -> {
            jenkinsClientAsync.jobList(new DefaultCallback<JobListEntity>() {
                @Override
                public void success(JobListEntity data) {
                    success.success(data);
                }
            });
        });
    }

    /**
     * build info for number
     * @param jobName
     * @param number
     * @param success
     * @param error
     */
    public void buildInfo(String jobName, int number, JenkinsSuccess<JobBuildInfo> success, JenkinsError error){
        run(() -> {
            try {
                JobBuildInfo jobEntity = jenkinsClientAsync.buildInfo(jobName, number);
                if (jobEntity != null){
                    success.success(jobEntity);
                    return;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            error.error();
        });
    }


    public void run(JenkinsRun jenkinsRun){
        ProgressManager instance = ProgressManager.getInstance();
        instance.run(new JenkinsTask(jenkinsRun, project));
    }

    public static class JenkinsTask extends Task.Backgroundable{

        private final JenkinsRun jenkinsRun;

        public JenkinsTask(JenkinsRun jenkinsRun, Project project) {
            super(project, "JenkinsTask");
            this.jenkinsRun = jenkinsRun;
        }

        @Override
        public void run(@NotNull ProgressIndicator indicator) {
            indicator.setText("Build");
            indicator.setIndeterminate(true);

            jenkinsRun.run();
        }
    }
}
