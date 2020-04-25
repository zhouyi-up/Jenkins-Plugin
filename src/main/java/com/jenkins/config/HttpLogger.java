package com.jenkins.config;

import okhttp3.logging.HttpLoggingInterceptor;

/**
 * @author liujun
 */
public class HttpLogger implements HttpLoggingInterceptor.Logger {
    @Override
    public void log(String message) {
        System.out.println(message);
    }
}
