package com.jenkins;

import com.intellij.ide.util.PropertiesComponent;
import org.apache.commons.lang3.StringUtils;

/**
 * @author liujun
 */
public class JenkinsPropertiesComponent {

    public static final String JENKINS_HOST = "jenkins.host";
    public static final String JENKINS_PASSWORD = "jenkins.pwd";
    public static final String JENKINS_USERNAME = "jenkins.username";
    public static final String JENKINS_CRUMB_ENABLE = "jenkins.enableCrumb";

    private static JenkinsPropertiesComponent jenkinsPropertiesComponent;

    public static JenkinsPropertiesComponent getInstance(){
        if (jenkinsPropertiesComponent == null){
            jenkinsPropertiesComponent = new JenkinsPropertiesComponent();
        }
        return jenkinsPropertiesComponent;
    }

    public  String getHost(){
        PropertiesComponent propertiesComponent = PropertiesComponent.getInstance();
        String value = propertiesComponent.getValue(JENKINS_HOST);
        if (StringUtils.isEmpty(value)){
            return "http://127.0.0.1:8080";
        }
        return value;
    }

    public  String getUser(){
        PropertiesComponent propertiesComponent = PropertiesComponent.getInstance();
        String value = propertiesComponent.getValue(JENKINS_USERNAME);
        if (StringUtils.isEmpty(value)){
            return "admin";
        }
        return value;
    }

    public  String getPwd(){
        PropertiesComponent propertiesComponent = PropertiesComponent.getInstance();
        String value = propertiesComponent.getValue(JENKINS_PASSWORD);
        if (StringUtils.isEmpty(value)){
            return "admin";
        }
        return value;
    }

    public  void setHost(String host){
        PropertiesComponent propertiesComponent = PropertiesComponent.getInstance();
        propertiesComponent.setValue(JENKINS_HOST,host);
    }

    public  void setUser(String user){
        PropertiesComponent propertiesComponent = PropertiesComponent.getInstance();
        propertiesComponent.setValue(JENKINS_USERNAME,user);
    }

    public  void setPwd(String pwd){
        PropertiesComponent propertiesComponent = PropertiesComponent.getInstance();
        propertiesComponent.setValue(JENKINS_PASSWORD,pwd);
    }

    public  int isInit(){
        PropertiesComponent propertiesComponent = PropertiesComponent.getInstance();
        String value = propertiesComponent.getValue("jenkins.init");
        if (StringUtils.isEmpty(value)){
            return 0;
        }
        return 1;
    }

    public  void setInit(){
        PropertiesComponent propertiesComponent = PropertiesComponent.getInstance();
        propertiesComponent.setValue("jenkins.init","1");
    }

    public  void setCrumbEnable(boolean crumbEnable){
        PropertiesComponent propertiesComponent = PropertiesComponent.getInstance();
        propertiesComponent.setValue(JENKINS_CRUMB_ENABLE,crumbEnable);
    }

    public  boolean getCrumbEnable(){
        PropertiesComponent propertiesComponent = PropertiesComponent.getInstance();
        return propertiesComponent.getBoolean(JENKINS_CRUMB_ENABLE);
    }
}
