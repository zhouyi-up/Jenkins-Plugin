package com.jenkins.compent;

import com.intellij.credentialStore.CredentialAttributes;
import com.intellij.credentialStore.CredentialAttributesKt;
import com.intellij.credentialStore.Credentials;
import com.intellij.ide.passwordSafe.PasswordSafe;
import com.intellij.ide.util.PropertiesComponent;
import org.apache.commons.lang3.StringUtils;

/**
 * @author liujun
 */
public class JenkinsSettingDataComponent {

    public static final String JENKINS_HOST = "jenkins.host";
    public static final String JENKINS_PASSWORD = "jenkins.pwd";
    public static final String JENKINS_USERNAME = "jenkins.username";
    public static final String JENKINS_CRUMB_ENABLE = "jenkins.enableCrumb";
    public static final String JENKINS_INITED = "jenkins.inited";

    private static JenkinsSettingDataComponent jenkinsSettingDataComponent;
    private PropertiesComponent propertiesComponent;
    private JenkinsSettingDataComponent(){
        propertiesComponent = PropertiesComponent.getInstance();
    }

    public static JenkinsSettingDataComponent getInstance(){
        if (jenkinsSettingDataComponent == null){
            jenkinsSettingDataComponent = new JenkinsSettingDataComponent();
        }
        return jenkinsSettingDataComponent;
    }

    public void saveHost(String host){
        propertiesComponent.setValue(JENKINS_HOST, host);
    }

    public String getHost(){
        String value = propertiesComponent.getValue(JENKINS_HOST);
        return StringUtils.isEmpty(value)?"http://127.0.0.1:8080/":value;
    }

    public void saveUsername(String username){
        propertiesComponent.setValue(JENKINS_USERNAME, username);
    }

    public String getUsername(){
        String value = propertiesComponent.getValue(JENKINS_USERNAME);
        return StringUtils.isEmpty(value)?"admin": value;
    }

    public void savePassword(String password){
        propertiesComponent.setValue(JENKINS_PASSWORD, password);
    }

    public String getPassword(){
        String password = propertiesComponent.getValue(JENKINS_PASSWORD);
        return StringUtils.isEmpty(password)?"admin":password;
    }

    public void saveEnableCrumb(boolean enableCrumb){
        propertiesComponent.setValue(JENKINS_CRUMB_ENABLE, enableCrumb);
    }

    public boolean getEnableCrumb(){
        return propertiesComponent.getBoolean(JENKINS_CRUMB_ENABLE);
    }

    public boolean getInited(){
        return propertiesComponent.getBoolean(JENKINS_INITED);
    }

    public void inited(){
        propertiesComponent.setValue(JENKINS_INITED, true);
    }
}
