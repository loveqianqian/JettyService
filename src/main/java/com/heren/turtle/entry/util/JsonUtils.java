/*
 * Copyright ( c ) 2016 Heren Tianjin Corporation. All Rights Reserved.
 *
 * This software is the confidential and proprietary information of Heren Tianjin
 * Corporation ("Confidential Information").  You shall not disclose such
 * Confidential Information and shall use it only in accordance with the terms
 * of the license agreement you entered into with Heren Tianjin Corporation or a
 * Heren Tianjin authorized reseller (the "License Agreement").
 */

package com.heren.turtle.entry.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * com.myloverqian.util JettyService
 * <p>
 * import fastJson
 * </p>
 *
 * @author zhiwei
 * @create 2016-07-20 13:38.
 */
public class JsonUtils extends JSON {

    /**
     * is json
     *
     * @param value
     * @return
     */
    public static boolean isJson(String value) {
        try {
            JSON.parse(value);
        } catch (JSONException e) {
            return false;
        }
        return true;
    }


    /**
     * JSONObject to map
     *
     * @param object
     */
    public static Map<String, Object> jsonToMap(JSONObject object) {
        Map<String, Object> map = new HashMap<>();
        for (String key : object.keySet()) {
            Object value = object.get(key);
            if (value instanceof JSONArray) {
                value = jsonToList((JSONArray) value);
            } else if (value instanceof JSONObject) {
                value = jsonToMap((JSONObject) value);
            }
            map.put(key, value);
        }
        return map;
    }

    /**
     * JSONObject to map
     *
     * @param object
     */
    public static ConcurrentMap<String, String> jsonToConcurrentMap(JSONObject object) {
        ConcurrentMap<String, String> map = new ConcurrentHashMap<>();
        for (String key : object.keySet()) {
            String value = (String) object.get(key);
            map.put(key, value);
        }
        return map;
    }

    /**
     * JSONArray to List
     *
     * @param array
     */
    public static List<Object> jsonToList(JSONArray array) {
        List<Object> list = new ArrayList<>();
        for (int i = 0; i < array.size(); i++) {
            Object value = array.get(i);
            if (value instanceof JSONArray) {
                value = jsonToList((JSONArray) value);
            } else if (value instanceof JSONObject) {
                value = jsonToMap((JSONObject) value);
            }
            list.add(value);
        }
        return list;
    }

    /**
     * JSONArray to List
     *
     * @param array
     */
    public static List<String> jsonToListString(JSONArray array) {
        List<String> list = new ArrayList<>();
        for (int i = 0; i < array.size(); i++) {
            String value = (String) array.get(i);
            list.add(value);
        }
        return list;
    }

}
