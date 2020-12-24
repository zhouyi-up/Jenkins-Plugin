package com.jenkins.ui.tooltab;

import javax.swing.tree.DefaultMutableTreeNode;

/**
 * @author liujun
 */
public class JenkinsLastBuildTreeNode extends DefaultMutableTreeNode {

    private String jobName;

    public JenkinsLastBuildTreeNode(String jobName){
        super("Run last build");
        this.jobName = jobName;
    }

    public String getJobName(){
        return jobName;
    }
}
