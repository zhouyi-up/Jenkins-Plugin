package com.jenkins.client;

/**
 * @author liujun
 */
public interface JenkinsSuccess<T> {

    /**
     * 请求成功
     * @param data
     */
    void success(T data);
}
