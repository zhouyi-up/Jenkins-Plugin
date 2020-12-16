package com.jenkins.ui;

import javax.swing.tree.DefaultMutableTreeNode;

/**
 * @author liujun
 */
public class JenkinsBuildTreeNode extends DefaultMutableTreeNode {

    private String jobName;

    public JenkinsBuildTreeNode(String jobName){
        super("Build");
        this.jobName = jobName;
    }

    public String getJobName(){
        return jobName;
    }
}
