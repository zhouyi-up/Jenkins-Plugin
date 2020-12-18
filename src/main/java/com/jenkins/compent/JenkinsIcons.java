package com.jenkins.compent;

import com.intellij.icons.AllIcons;
import com.intellij.openapi.util.IconLoader;
import com.intellij.ui.IconManager;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;

/**
 * @author liujun
 */
public class JenkinsIcons {

    public static Icon getIcon(String iconFilename) {
        return IconLoader.getIcon(iconFilename, JenkinsIcons.class);
    }

    public static final Icon REFRESH = AllIcons.Actions.Refresh;

    public static final Icon JENKINS_LOGO = getIcon("/image/pluginIcon.svg");
}
