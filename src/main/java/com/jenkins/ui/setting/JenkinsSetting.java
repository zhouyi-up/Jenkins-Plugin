package com.jenkins.ui.setting;

import com.intellij.openapi.options.Configurable;
import com.intellij.openapi.options.ConfigurationException;
import com.jenkins.compent.JenkinsSettingDataComponent;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;

/**
 * @author liujun
 */
public class JenkinsSetting implements Configurable {

    private JenkinsSettingView jenkinsSettingView;
    private JenkinsSettingDataComponent dataComponent;

    public JenkinsSetting(){
        dataComponent = JenkinsSettingDataComponent.getInstance();

        JenkinsSettingView.Setting setting = new JenkinsSettingView.Setting();
        setting.setCrumb(dataComponent.getEnableCrumb());
        setting.setHost(dataComponent.getHost());
        setting.setPassword(dataComponent.getPassword());
        setting.setUsername(dataComponent.getUsername());

        jenkinsSettingView = new JenkinsSettingView(setting);
    }

    @Override
    public String getDisplayName() {
        return "Jenkins Tools";
    }

    @Override
    public @Nullable JComponent createComponent() {
        return jenkinsSettingView;
    }

    @Override
    public boolean isModified() {
        return jenkinsSettingView.isModified();
    }

    @Override
    public void apply() throws ConfigurationException {

    }
}
