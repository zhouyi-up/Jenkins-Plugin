package com.jenkins.client;

import java.util.Base64;

/**
 * @author liujun
 */
public final class AuthUtils {

    public static String auth(String username, String password) {
        return "Basic "+ Base64.getUrlEncoder().encodeToString((username+":"+password).getBytes());
    }
}
