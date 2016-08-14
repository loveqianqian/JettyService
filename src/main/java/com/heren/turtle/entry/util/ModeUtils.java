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
import com.alibaba.fastjson.JSONObject;
import net.sf.json.xml.XMLSerializer;

/**
 * com.myloverqian.util JettyService
 *
 * @author zhiwei
 * @create 2016-07-20 13:37.
 */
public class ModeUtils {

    /**
     * the type of value
     */
    public static final int TYPE_XML = 0x000010;
    public static final int TYPE_JSON = 0x000020;
    public static final int TYPE_UN_DEFINITION = 0x000404;

    /**
     * to judge the type of the value
     *
     * @param value
     * @return
     */
    public static int whichMode(String value) {
        if (JsonUtils.isJson(value)) {
            return TYPE_JSON;
        } else if (XmlUtils.isXml(value)) {
            return TYPE_XML;
        } else {
            return TYPE_UN_DEFINITION;
        }
    }

    /**
     * transform xmlString to jsonString
     *
     * @param xmlString
     * @return
     */
    public static String xmlToJsonString(String xmlString) {
        XMLSerializer serializer = new XMLSerializer();
        return serializer.read(xmlString).toString();
    }

    /**
     * transform xmlString to json
     *
     * @param xmlString
     * @return
     */
    public static JSONObject xmlToJson(String xmlString) {
        return JSON.parseObject(xmlToJsonString(xmlString));
    }

    /**
     * @param value
     * @return
     */
    public static JSONObject allModeToJson(String value) {
        JSONObject json = null;
        switch (whichMode(value)) {
            case TYPE_JSON:
                json = JSON.parseObject(value);
                break;
            case TYPE_XML:
                json = xmlToJson(value);
                break;
            case TYPE_UN_DEFINITION:
                break;
        }
        return json;
    }

}
