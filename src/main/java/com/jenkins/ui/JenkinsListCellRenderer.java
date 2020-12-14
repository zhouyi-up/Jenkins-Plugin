package com.jenkins.ui;

import com.intellij.ui.components.JBPanel;

import javax.swing.*;
import java.awt.*;

/**
 * @author corel
 */
public class JenkinsListCellRenderer implements ListCellRenderer<JobListItemView> {
    @Override
    public Component getListCellRendererComponent(JList<? extends JobListItemView> list, JobListItemView value,
                                                  int index, boolean isSelected, boolean cellHasFocus) {

        if (isSelected){
            value.setBackground(list.getSelectionBackground());
            value.setForeground(list.getSelectionForeground());
        }else {
            value.setBackground(list.getBackground());
            value.setForeground(list.getForeground());
        }
        return value;
    }
}
