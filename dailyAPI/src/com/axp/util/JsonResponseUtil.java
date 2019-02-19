package com.axp.util;

import java.util.HashMap;
import java.util.Map;

public class JsonResponseUtil {

    /**
     * 返回只包含状态和操作信息的josn；
     *
     * @param status
     * @param message
     * @return
     */
    public static Map<String, Object> getJson(Integer status, String message) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("status", status);
        map.put("message", message);
        return map;
    }

    /**
     * 返回包含状态和操作信息和返回信息的josn；
     *
     * @param status  返回状态；
     * @param message 操作信息；
     * @param data    返回的数据信息；
     * @return
     */
    public static Map<String, Object> getJson(Integer status, String message, Map<String, Object> data) {
        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("status", status);
        resultMap.put("message", message);
        resultMap.put("data", data);
        return resultMap;
    }
}
