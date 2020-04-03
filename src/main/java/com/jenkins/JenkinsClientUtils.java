package com.jenkins;

import com.jenkins.model.JobEntity;
import com.jenkins.model.JobListEntity;

import java.util.Map;

/**
 * @author liujun
 */
public class JenkinsClientUtils {

    private static JenkinsClient jenkinsClient;

    private JenkinsClientUtils(){};

    private JenkinsClientUtils(String host,String user,String pwd){
        if (jenkinsClient == null){
            jenkinsClient = new JenkinsClient(host,user,pwd);
        }
    }

    public static JenkinsClientUtils getInstance(){
        return new JenkinsClientUtils(JenkinsPropertiesComponent.getHost(),
                JenkinsPropertiesComponent.getUser(),JenkinsPropertiesComponent.getPwd());
    }

    public JobListEntity getAllJob(){
        JenkinsResponse<JobListEntity> response = jenkinsClient.queryAllJob();
        if (response.isSuccess()){
            return response.getBody();
        }
        return null;
    }

    public JobEntity getJob(String jobName){
        JenkinsResponse<JobEntity> resp = jenkinsClient.queryJobInfo(jobName);
        if (resp.isSuccess()){
            return resp.getBody();
        }
        return null;
    }

    public JenkinsResponse build(String jobName, Map<Object,Object> paramMap){
        BuildParam buildParam = new BuildParam();
        buildParam.setJobName(jobName);

        if (paramMap.isEmpty()){
            return jenkinsClient.buildNoParam(buildParam);
        }

        buildParam.addParamForObject(paramMap);
        return jenkinsClient.buildParam(buildParam);
    }
}
