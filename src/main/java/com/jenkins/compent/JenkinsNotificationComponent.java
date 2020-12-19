package com.jenkins.compent;

import com.intellij.notification.Notification;
import com.intellij.notification.NotificationType;
import com.intellij.openapi.project.Project;

/**
 * @author liujun
 */
public class JenkinsNotificationComponent {

    public static final String JENKINS_GROUP = "Jenkins Group";

    public static void notifyError(Project project, String content) {
        Notification notification = new Notification(JENKINS_GROUP, "", content, NotificationType.ERROR);
        notification.notify(project);
    }

    public static void notifyError(Project project,String title, String content) {
        Notification notification = new Notification(JENKINS_GROUP, title, content, NotificationType.ERROR);
        notification.notify(project);
    }

    public static void notifySuccess(Project project, String content){
        Notification notification = new Notification(JENKINS_GROUP, "", content, NotificationType.INFORMATION);
        notification.notify(project);
    }

    public static void notifyWarning(Project project, String content){
        Notification notification = new Notification(JENKINS_GROUP, "", content, NotificationType.WARNING);
        notification.notify(project);
    }

}
