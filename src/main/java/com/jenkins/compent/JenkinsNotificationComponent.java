package com.jenkins.compent;
import com.intellij.openapi.ui.popup.Balloon;
import com.intellij.notification.Notification.CollapseActionsDirection;
import javax.swing.Icon;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.notification.NotificationListener;

import com.intellij.notification.Notification;
import com.intellij.notification.NotificationType;
import com.intellij.openapi.project.Project;

/**
 * @author liujun
 */
public class JenkinsNotificationComponent {

    public static final String JENKINS_GROUP = "Jenkins Group";

    public static void notifyError(Project project, String content) {
        Notification notification = new Notification(JENKINS_GROUP, "",content, NotificationType.ERROR);
        notification.setContent(content);
        notification.notify(project);
    }

    public static void notifyError(Project project,String title, String content) {
        Notification notification = new Notification(JENKINS_GROUP, "",content, NotificationType.ERROR);
        notification.setContent(content);
        notification.setTitle(title);
        notification.notify(project);
    }

    public static void notifySuccess(Project project, String content){
        Notification notification = new Notification(JENKINS_GROUP, "",content, NotificationType.INFORMATION);
        notification.setContent(content);
        notification.notify(project);
    }

    public static void notifySuccess(Project project,String title, String content){
        NotificationGroupManager.getInstance().getNotificationGroup(JENKINS_GROUP)
                .createNotification(content, NotificationType.INFORMATION)
                .setTitle(title)
                .notify(project);
    }

    public static void notifyWarning(Project project, String content){
        Notification notification = new Notification(JENKINS_GROUP, "",content, NotificationType.WARNING);
        notification.setContent(content);
        notification.notify(project);
    }

    public static void notifyWarning(Project project,String title, String content){
        NotificationGroupManager.getInstance().getNotificationGroup(JENKINS_GROUP)
                .createNotification(content, NotificationType.WARNING)
                .setTitle(title)
                .notify(project);
    }

}
