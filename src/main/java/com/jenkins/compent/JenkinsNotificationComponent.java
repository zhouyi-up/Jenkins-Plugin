package com.jenkins.compent;

import com.intellij.notification.NotificationGroupManager;
import com.intellij.notification.NotificationType;
import com.intellij.openapi.project.Project;

/**
 * @author liujun
 */
public class JenkinsNotificationComponent {

    public static final String JENKINS_GROUP = "Jenkins Group";

    public static void notifyError(Project project, String content) {
        NotificationGroupManager.getInstance().getNotificationGroup(JENKINS_GROUP)
                .createNotification(content, NotificationType.ERROR)
                .notify(project);
    }
}
