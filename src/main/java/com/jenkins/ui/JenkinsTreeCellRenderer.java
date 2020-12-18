package com.jenkins.ui;

import com.jenkins.compent.JenkinsIcons;

import javax.swing.*;
import javax.swing.tree.DefaultTreeCellRenderer;
import java.awt.*;

/**
 * @author liujun
 */
public class JenkinsTreeCellRenderer extends DefaultTreeCellRenderer {

    public JenkinsTreeCellRenderer(){

    }

    @Override
    public Component getTreeCellRendererComponent(JTree tree, Object value,
                                                  boolean sel, boolean expanded, boolean leaf, int row,
                                                  boolean hasFocus) {
        Component treeCellRendererComponent = super.getTreeCellRendererComponent(tree,
                value, sel, expanded, leaf, row, hasFocus);
        System.out.println(value.getClass());
        if (value instanceof JenkinsTreeNode){
            setOpenIcon(JenkinsIcons.REFRESH);
            setClosedIcon(JenkinsIcons.REFRESH);
        }
        if (value instanceof JenkinsRootTreeNode){
            setOpenIcon(JenkinsIcons.JENKINS_LOGO);
            setClosedIcon(JenkinsIcons.JENKINS_LOGO);
        }

        return treeCellRendererComponent;
    }
}

