package com.jenkins.utils;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.List;


/**
 * @author liujun
 * @date 2019/12/10 15:26
 */
public final class JsonUtils {

    private JsonUtils() {
    }

    private static final ObjectMapper MAPPER;

    static {
        MAPPER = new ObjectMapper();
        MAPPER.configure(JsonParser.Feature.ALLOW_COMMENTS, true);
        MAPPER.configure(JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES, true);
        MAPPER.configure(JsonParser.Feature.ALLOW_SINGLE_QUOTES, true);
        MAPPER.configure(JsonParser.Feature.ALLOW_UNQUOTED_CONTROL_CHARS, true);
        MAPPER.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

    public static String toJsonString(Object value) {
        try {
            return MAPPER.writeValueAsString(value);
        } catch (JsonProcessingException e) {
            throw new IllegalArgumentException("to json fail:" + value, e);
        }
    }

    public static <T> T parseObject(String jsonString, Class<T> type) {
        try {
            return MAPPER.readValue(jsonString, type);
        } catch (IOException e) {
            throw new IllegalArgumentException("parse json fail:" + jsonString, e);
        }
    }

    public static <T> List<T> parseArray(String jsonString, Class<T> type) {
        try {
            JavaType javaType = MAPPER.getTypeFactory().constructParametricType(List.class, type);
            return MAPPER.readValue(jsonString, javaType);
        } catch (IOException e) {
            throw new IllegalArgumentException("parse json fail:" + jsonString, e);
        }
    }

}
