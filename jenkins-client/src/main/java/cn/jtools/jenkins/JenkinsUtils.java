package cn.jtools.jenkins;

import java.util.Base64;

/**
 * @author liujun
 */
public final class JenkinsUtils {

    public static String auth(String username, String password) {
        return "Basic "+ Base64.getUrlEncoder().encodeToString((username+":"+password).getBytes());
    }
}
