package com.jenkins.ui.tooltab;

import com.google.common.collect.Maps;
import com.intellij.openapi.project.Project;
import com.intellij.ui.treeStructure.Tree;
import com.jenkins.client.BuildParam;
import com.jenkins.compent.JenkinsComponent;
import com.jenkins.compent.JenkinsNotificationComponent;
import com.jenkins.model.JobEntity;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


/**
 * @author liujun
 */
public class JenkinsMainMouseAdapter extends MouseAdapter {

    public static final int CLICK_COUNT = 2;

    private Map<String, JobEntity> jobMap;
    private Project project;
    private JenkinsComponent jenkinsComponent;

    public JenkinsMainMouseAdapter(Project project, Map<String, JobEntity> jobMap, JenkinsComponent jenkinsComponent){
        this.project = project;
        this.jobMap = jobMap;
        this.jenkinsComponent = jenkinsComponent;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        Component component = e.getComponent();
        if (!(component instanceof Tree)){
            super.mouseClicked(e);
            return;
        }
        Tree jTree = (Tree) component;
        Component deepestRendererComponentAt = jTree.getDeepestRendererComponentAt(e.getX(), e.getY());
        if (deepestRendererComponentAt == null){
            return;
        }

        Object lastSelectedPathComponent = jTree.getLastSelectedPathComponent();
        if (lastSelectedPathComponent == null){
            return;
        }

        if (lastSelectedPathComponent instanceof JenkinsBuildTreeNode){
            JenkinsBuildTreeNode selectNode = (JenkinsBuildTreeNode) lastSelectedPathComponent;
            String jobName = selectNode.getJobName();
            if (e.getClickCount() >= CLICK_COUNT){
                JobEntity jobBean = jobMap.get(jobName);
                if (jobBean == null){
                    return;
                }

                boolean isParam = true;

                java.util.List<JobEntity.PropertyBean> property = jobBean.getProperty();
                if (property.isEmpty()){
                    isParam = false;
                }
                List<JobEntity.PropertyBean> collect = property.stream()
                        .filter(item -> "hudson.model.ParametersDefinitionProperty".equals(item.get_class()))
                        .collect(Collectors.toList());
                if (collect.isEmpty()){
                    isParam = false;
                }

                if (!isParam){
                    jenkinsComponent.build(jobName, data -> {
                        JenkinsNotificationComponent.notifySuccess(project, "Commit Successful!");
                    }, ()->{});
                    return;
                }
                JenkinsBuildView jenkinsBuildView = new JenkinsBuildView(jobBean);
                boolean b = jenkinsBuildView.showAndGet();
                if (b){
                    Map<Object, Object> buildParamMap = jenkinsBuildView.getBuildParamMap();

                    BuildParam buildParam = new BuildParam();
                    buildParam.setJobName(jobBean.getName());
                    buildParam.addParamForObject(buildParamMap);

                    jenkinsComponent.build(buildParam,data -> {
                        JenkinsNotificationComponent.notifySuccess(project, "Commit Successful!");
                    }, ()->{});
                }
            }
        }
    }
}
