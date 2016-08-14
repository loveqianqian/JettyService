/*
 * Copyright ( c ) 2016 Heren Tianjin Corporation. All Rights Reserved.
 *
 * This software is the confidential and proprietary information of Heren Tianjin
 * Corporation ("Confidential Information").  You shall not disclose such
 * Confidential Information and shall use it only in accordance with the terms
 * of the license agreement you entered into with Heren Tianjin Corporation or a
 * Heren Tianjin authorized reseller (the "License Agreement").
 */

package com.heren.turtle.entry.core;

import com.alibaba.fastjson.JSONObject;

import com.heren.turtle.entry.constant.GlobalConstant;
import com.heren.turtle.entry.util.ModeUtils;
import org.dom4j.DocumentException;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;
import java.util.Set;

/**
 * Created by zhiwei on 2016/6/23.
 */
public class ClientCheck {

    private JSONObject jsonObject;

    public JSONObject getJsonObject() {
        return jsonObject;
    }

    public void setJsonObject(JSONObject jsonObject) {
        this.jsonObject = jsonObject;
    }

    enum headersElement {
        sendTime,
        msgSerial,
        msgCode,
        sysCode
    }

    /**
     * @param message
     * @param standard
     * @return
     * @throws DocumentException
     */
    public List<String> checkLegitimate(String message, TemplateReader.Template standard) throws DocumentException {
        List<String> lackOfElements = new ArrayList<>();
        jsonObject = ModeUtils.allModeToJson(message);
        if (jsonObject != null) {
            if (jsonObject.containsKey(GlobalConstant.HEAD)) {
                JSONObject headersJsonObject = jsonObject.getJSONObject(GlobalConstant.HEAD);
                EnumSet<headersElement> elements = EnumSet.allOf(headersElement.class);
                for (headersElement element : elements) {
                    String name = element.name();
                    if (!headersJsonObject.containsKey(name)) {
                        lackOfElements.add(name);
                    }
                }
            } else {
                lackOfElements.add(GlobalConstant.HEAD);
            }
            if (jsonObject.containsKey(GlobalConstant.PAYLOAD)) {
                JSONObject payloadJsonObject = jsonObject.getJSONObject(GlobalConstant.PAYLOAD);
                if (standard != null) {
                    Set<TemplateReader.Property> request = standard.getRequest();
                    Set<TemplateReader.Property> response = standard.getResponse();
                    if (request != null) {
                        JSONObject requestJsonObject = payloadJsonObject.getJSONObject(GlobalConstant.REQUEST);
                        matchElements(requestJsonObject, lackOfElements, request);
                    }
                    if (response != null) {
                        JSONObject responseJsonObject = payloadJsonObject.getJSONObject(GlobalConstant.RESPONSE);
                        matchElements(responseJsonObject, lackOfElements, response);
                    }
                }
            } else {
                lackOfElements.add(GlobalConstant.PAYLOAD);
            }
            return lackOfElements;
        } else {
            throw new RuntimeException("Element type can not be resolved!");
        }
    }

    public List<String> matchElements(JSONObject message, List<String> lackOfElements, Set<TemplateReader.Property> template) {
        for (TemplateReader.Property property : template) {
            String propertyName = property.getName();
            if (property.getSubProperties() != null) {
                if (message.containsKey(propertyName)) {
                    JSONObject subJsonObject = message.getJSONObject(propertyName);
                    matchElements(subJsonObject, lackOfElements, property.getSubProperties());
                } else {
                    lackOfElements.add(propertyName);
                }
            } else {
                if (!property.isNullable()) {
                    if (!message.containsKey(propertyName)) {
                        lackOfElements.add(propertyName);
                    }
                }
            }
        }
        return lackOfElements;
    }

}
