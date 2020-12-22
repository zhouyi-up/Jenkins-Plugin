package com.jenkins.ui.setting;

import com.intellij.ui.components.JBLabel;

import javax.swing.*;
import java.awt.*;

/**
 * @author liujun
 */
public class JenkinsSettingView extends JPanel {

    private Setting setting;
    private boolean isModified = false;

    public JenkinsSettingView(Setting setting){
        this.setting = setting;
        init();
    }

    private void init(){
        setLayout(new GridLayout(3,2));
        JBLabel jbLabel = new JBLabel("Jenkins server");
        add(jbLabel,0,0);

    }


    public boolean isModified(){
        return isModified;
    }

    /**
     * bind for ui setting model
     */
    public static class Setting{
        private String host;
        private String username;
        private String password;
        private Boolean crumb;

        public String getHost() {
            return host;
        }

        public void setHost(String host) {
            this.host = host;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public Boolean getCrumb() {
            return crumb;
        }

        public void setCrumb(Boolean crumb) {
            this.crumb = crumb;
        }
    }
}
