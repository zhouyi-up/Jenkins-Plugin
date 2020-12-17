package com.jenkins.compent;

import com.intellij.openapi.progress.ProgressIndicator;
import com.intellij.openapi.progress.ProgressManager;
import com.intellij.openapi.progress.Task;
import com.intellij.openapi.project.Project;
import com.jenkins.client.*;
import com.jenkins.model.JobEntity;
import com.jenkins.model.JobListEntity;
import org.jetbrains.annotations.NotNull;

/**
 * @author liujun
 */
public class JenkinsComponent {

    private static JenkinsComponent jenkinsComponent;

    private JenkinsClientAsync jenkinsClientAsync;
    private JenkinsSettingDataComponent settingData;
    private Project project;

    private JenkinsComponent(Project project){
        this.project = project;
        settingData = JenkinsSettingDataComponent.getInstance();
        initJenkinsClient();
    }

    private void initJenkinsClient() {
        jenkinsClientAsync = new JenkinsClientAsync(settingData.getHost(),
                settingData.getUsername(), settingData.getPassword(),
                settingData.getEnableCrumb());
    }

    public static JenkinsComponent getInstance(Project project){
        if (jenkinsComponent == null){
            jenkinsComponent = new JenkinsComponent(project);
        }
        return jenkinsComponent;
    }

    public void build(String jobName, JenkinsSuccess<String> jenkinsSuccess, JenkinsError jenkinsError){
        run(() -> {
            jenkinsClientAsync.build(jobName, new DefaultCallback<>() {
                @Override
                public void success(String data) {
                    jenkinsSuccess.success(data);
                }

                @Override
                public void error(Exception exception) {
                    super.error(exception);
                    jenkinsError.error();
                }
            });
        });
    }

    public void build(BuildParam buildParam, JenkinsSuccess<String> jenkinsSuccess, JenkinsError jenkinsError){
        run(() -> {

            jenkinsClientAsync.build(buildParam, new DefaultCallback<>() {
                @Override
                public void success(String data) {
                    jenkinsSuccess.success(data);
                }

                @Override
                public void error(Exception exception) {
                    super.error(exception);
                    jenkinsError.error();
                }
            });
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


    public void run(JenkinsRun jenkinsRun){
        ProgressManager instance = ProgressManager.getInstance();
        instance.run(new JenkinsTask(jenkinsRun));
    }

    public class JenkinsTask extends Task.Backgroundable{

        private JenkinsRun jenkinsRun;

        public JenkinsTask(JenkinsRun jenkinsRun) {
            super(project, "JenkinsTask");
            this.jenkinsRun = jenkinsRun;
        }

        @Override
        public void run(@NotNull ProgressIndicator indicator) {
            indicator.setText("test");
            indicator.setIndeterminate(true);

            jenkinsRun.run();
        }
    }
}
