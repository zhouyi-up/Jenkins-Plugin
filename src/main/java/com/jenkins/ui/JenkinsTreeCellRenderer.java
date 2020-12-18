package com.jenkins.ui;

import com.intellij.ui.ColoredTreeCellRenderer;
import com.jenkins.compent.JenkinsIcons;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;
import java.awt.*;

/**
 * @author liujun
 */
public class JenkinsTreeCellRenderer extends ColoredTreeCellRenderer {

    public JenkinsTreeCellRenderer(){

    }

    @Override
    public void customizeCellRenderer(@NotNull JTree tree, Object value,
                                      boolean selected, boolean expanded, boolean leaf, int row,
                                      boolean hasFocus) {
        if (value instanceof DefaultMutableTreeNode){
            DefaultMutableTreeNode node = (DefaultMutableTreeNode) value;
            Object userObject = node.getUserObject();
            if (value instanceof JenkinsRootTreeNode){
                append("Jenkins");
                setIcon(JenkinsIcons.JENKINS_LOGO);
            }
            if (value instanceof JenkinsTreeNode){
                append(String.valueOf(userObject));
                setIcon(JenkinsIcons.JOB);
            }
            if (value instanceof JenkinsBuildTreeNode){
                append("Build");
                setIcon(JenkinsIcons.BUILD);
            }
        }
    }
}

