package com.jenkins.compent;

import com.intellij.icons.AllIcons;
import com.intellij.openapi.util.IconLoader;

import javax.swing.*;

/**
 * @author liujun
 */
public class JenkinsIcons {

    public static Icon getIcon(String iconFilename) {
        return IconLoader.getIcon(iconFilename, JenkinsIcons.class);
    }

    public static final Icon REFRESH = AllIcons.Actions.Refresh;

    public static final Icon COLLAPSEALL = AllIcons.Actions.Collapseall;
    public static final Icon EXPANDALL = AllIcons.Actions.Expandall;


    public static final Icon JENKINS_LOGO = getIcon("/image/jenkins_icon.png");

    public static final Icon BUILD = AllIcons.Actions.Execute;

    public static final Icon JOB = AllIcons.Nodes.MultipleTypeDefinitions;
}
