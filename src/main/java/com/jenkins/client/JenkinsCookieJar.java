package com.jenkins.client;


import com.google.common.collect.Maps;
import okhttp3.Cookie;
import okhttp3.CookieJar;
import okhttp3.HttpUrl;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author liujun
 */
public class JenkinsCookieJar implements CookieJar {

    private final Map<String, List<Cookie>> cookieStore = Maps.newConcurrentMap();
    private static JenkinsCookieJar jenkinsCookieJar;

    public JenkinsCookieJar(){}

    public static JenkinsCookieJar getInstance(){
        if (jenkinsCookieJar == null){
            jenkinsCookieJar = new JenkinsCookieJar();
        }
        return jenkinsCookieJar;
    }

    @NotNull
    @Override
    public List<Cookie> loadForRequest(@NotNull HttpUrl httpUrl) {
        List<Cookie> cookies = cookieStore.get(httpUrl.host());
        return cookies != null ? cookies : new ArrayList<Cookie>();
    }

    @Override
    public void saveFromResponse(@NotNull HttpUrl httpUrl, @NotNull List<Cookie> list) {
        if (list == null){
            return;
        }
        cookieStore.put(httpUrl.host(), list);
    }
}
