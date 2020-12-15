package com.jenkins.ui;

import javax.swing.tree.DefaultMutableTreeNode;

/**
 * @author liujun
 */
public class JenkinsTreeNode extends DefaultMutableTreeNode {

    private String jobName;

    public JenkinsTreeNode(String jobName){
        super(jobName);

        this.jobName = jobName;
        DefaultMutableTreeNode build = new DefaultMutableTreeNode("Build");
        add(build);
    }

    public String getJobName(){
        return jobName;
    }
}
