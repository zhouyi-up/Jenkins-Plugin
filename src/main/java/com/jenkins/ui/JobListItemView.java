package com.jenkins.ui;

import com.intellij.icons.AllIcons;
import com.intellij.ui.components.JBLabel;
import com.intellij.util.ui.JBImageIcon;

import javax.swing.*;
import java.awt.*;

/**
 * @author corel
 */
public class JobListItemView extends JPanel {

    JBLabel jobNameLabel;

    public JobListItemView(String jobName){

        jobNameLabel = new JBLabel(jobName);
        jobNameLabel.setIcon(AllIcons.Actions.BuildLoadChanges);

        add(jobNameLabel);
    }


    public String getJobName(){
        return jobNameLabel.getText();
    }


    @Override
    public String toString() {
        return getJobName();
    }

}
