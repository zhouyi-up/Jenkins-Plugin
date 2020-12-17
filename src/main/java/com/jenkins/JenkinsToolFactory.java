package com.jenkins;

import com.intellij.openapi.project.Project;
import com.intellij.openapi.wm.ToolWindow;
import com.intellij.openapi.wm.ToolWindowFactory;
import com.intellij.ui.content.Content;
import com.intellij.ui.content.ContentFactory;
import com.jenkins.client.JenkinsClientAsync;
import com.jenkins.ui.JenkinsMain;
import org.jetbrains.annotations.NotNull;

/**
 * @author liujun
 */
public class JenkinsToolFactory implements ToolWindowFactory {

    @Override
    public void createToolWindowContent(@NotNull Project project, @NotNull ToolWindow toolWindow) {
        JenkinsClientAsync jenkinsClientAsync = new JenkinsClientAsync("http://127.0.0.1:8080/", "admin", "Corele1024.", true);
        JenkinsMain jenkinsMain = new JenkinsMain(jenkinsClientAsync, project);

        ContentFactory contentFactory = ContentFactory.SERVICE.getInstance();
        Content content = contentFactory.createContent(jenkinsMain, "", false);
        toolWindow.getContentManager().addContent(content);
    }
}
