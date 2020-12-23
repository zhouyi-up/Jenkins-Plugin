package com.jenkins.client;

import okhttp3.logging.HttpLoggingInterceptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author liujun
 */
public class HttpLogger implements HttpLoggingInterceptor.Logger {


    @Override
    public void log(String message) {
        System.out.println(message);
    }
}
