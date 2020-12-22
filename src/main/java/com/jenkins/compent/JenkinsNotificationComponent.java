package com.jenkins.compent;

import com.intellij.notification.Notification;
import com.intellij.notification.NotificationGroupManager;
import com.intellij.notification.NotificationType;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.project.ProjectManager;

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

    public static void notifyError(Project project,String title, String content) {
        Notification notification = NotificationGroupManager.getInstance().getNotificationGroup(JENKINS_GROUP)
                .createNotification(content, NotificationType.ERROR);
        notification.setTitle(title);
        notification.notify(project);
    }

    public static void notifySuccess(Project project, String content){
        NotificationGroupManager.getInstance().getNotificationGroup(JENKINS_GROUP)
                .createNotification(content, NotificationType.INFORMATION)
                .notify(project);
    }

    public static void notifySuccess(Project project,String title, String content){
        NotificationGroupManager.getInstance().getNotificationGroup(JENKINS_GROUP)
                .createNotification(content, NotificationType.INFORMATION)
                .setTitle(title)
                .notify(project);
    }

    public static void notifyWarning(Project project, String content){
        NotificationGroupManager.getInstance().getNotificationGroup(JENKINS_GROUP)
                .createNotification(content, NotificationType.WARNING)
                .notify(project);
    }

}
