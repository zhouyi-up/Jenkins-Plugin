package com.jenkins;

import java.util.HashMap;
import java.util.Map;

/**
 * @author liujun
 */
public class BuildParam {

    private String jobName;
    private Map<String,String> param = new HashMap<>();

    public String getJobName() {
        return jobName;
    }

    public void setJobName(String jobName) {
        this.jobName = jobName;
    }

    public Map<String, String> getParam() {
        return param;
    }

    public void addParam(String key,String value){
        param.put(key,value);
    }

    public void addParam(Map<String,String> paramMap){
        param.putAll(paramMap);
    }

    public void addParamForObject(Map<Object,Object> paramMap){
        for (Object obj : paramMap.keySet()) {
            if (paramMap.get(obj) != null){
                param.put(String.valueOf(obj),String.valueOf(paramMap.get(obj)));
            }
        }
    }
}
