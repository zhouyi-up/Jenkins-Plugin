package com.jenkins;

import java.io.Serializable;

/**
 * @author liujun
 */
public class JenkinsResponse<T> implements Serializable {

    public static final String SUCCESS = "10000";
    public static final String FAIL = "40000";

    private String errorMsg;
    private String errorCode;

    private T body;

    public boolean isSuccess(){
        if (errorCode.equalsIgnoreCase(SUCCESS)){
            return true;
        }
        return false;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public T getBody() {
        return body;
    }

    public void setBody(T body) {
        this.body = body;
    }
}
