package com.jenkins.compent;

import com.intellij.credentialStore.CredentialAttributes;
import com.intellij.credentialStore.CredentialAttributesKt;
import com.intellij.credentialStore.Credentials;
import com.intellij.ide.passwordSafe.PasswordSafe;
import com.intellij.ide.util.PropertiesComponent;

/**
 * @author liujun
 */
public class JenkinsSettingDataComponent {

    public static final String JENKINS_HOST = "jenkins.host";
    public static final String JENKINS_PASSWORD = "jenkins.pwd";
    public static final String JENKINS_USERNAME = "jenkins.username";
    public static final String JENKINS_CRUMB_ENABLE = "jenkins.enableCrumb";

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
        return propertiesComponent.getValue(JENKINS_HOST);
    }

    public void saveUsername(String username){
        propertiesComponent.setValue(JENKINS_USERNAME, username);
    }

    public String getUsername(){
        return propertiesComponent.getValue(JENKINS_USERNAME);
    }

    public void savePassword(String password){
        CredentialAttributes credentialAttributes =
                new CredentialAttributes(CredentialAttributesKt.generateServiceName("Test", JENKINS_PASSWORD));
        Credentials credentials = new Credentials("JENKINS_USERNAME", password);
        PasswordSafe.getInstance().set(credentialAttributes,credentials);
    }

    public String getPassword(){
        CredentialAttributes credentialAttributes =
                new CredentialAttributes(CredentialAttributesKt.generateServiceName("Test", JENKINS_PASSWORD));
        return PasswordSafe.getInstance().getPassword(credentialAttributes);
    }

    public void saveEnableCrumb(boolean enableCrumb){
        propertiesComponent.setValue(JENKINS_CRUMB_ENABLE, enableCrumb);
    }

    public boolean getEnableCrumb(){
        return propertiesComponent.getBoolean(JENKINS_CRUMB_ENABLE);
    }

}
