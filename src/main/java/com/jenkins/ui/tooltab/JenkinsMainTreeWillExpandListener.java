package com.jenkins.ui.tooltab;

import com.intellij.openapi.project.Project;
import com.jenkins.compent.JenkinsComponent;
import com.jenkins.model.JobEntity;

import javax.swing.event.TreeExpansionEvent;
import javax.swing.event.TreeWillExpandListener;
import javax.swing.tree.ExpandVetoException;
import javax.swing.tree.TreePath;
import java.util.Map;

/**
 * @author liujun
 */
public class JenkinsMainTreeWillExpandListener implements TreeWillExpandListener {

    private Map<String, JobEntity> jobMap;
    private Project project;
    private JenkinsComponent jenkinsComponent;

    public JenkinsMainTreeWillExpandListener(Map<String, JobEntity> jobMap, Project project,
                                             JenkinsComponent jenkinsComponent) {
        this.jobMap = jobMap;
        this.project = project;
        this.jenkinsComponent = jenkinsComponent;
    }

    @Override
    public void treeWillExpand(TreeExpansionEvent event) throws ExpandVetoException {
        TreePath path = event.getPath();
        if (path == null){
            return;
        }
        Object lastPathComponent = path.getLastPathComponent();
        if (lastPathComponent == null){
            return;
        }
        if (lastPathComponent instanceof JenkinsRootTreeNode){

        }
        if (lastPathComponent instanceof JenkinsTreeNode){
            JenkinsTreeNode jenkinsTreeNode = (JenkinsTreeNode) lastPathComponent;
            String jobName = jenkinsTreeNode.getJobName();
            jenkinsComponent.jobInfo(jobName, data -> {
                jobMap.put(jobName, data);
            }, () -> {});
        }
    }

    @Override
    public void treeWillCollapse(TreeExpansionEvent event) throws ExpandVetoException {

    }
}
