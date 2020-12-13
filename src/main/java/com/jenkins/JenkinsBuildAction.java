package com.jenkins;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author corel
 */
public class JenkinsBuildAction extends AnAction {

    @Override
    public void actionPerformed(AnActionEvent e) {
        System.out.println("The Jenkins open window !");
    }
}
