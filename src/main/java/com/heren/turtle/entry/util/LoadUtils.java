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
import com.heren.turtle.entry.constant.GlobalConstant;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by zhiwei on 2016/5/31.
 */
public class LoadUtils {

    /**
     * the type of value
     */
    public static final int TYPE_USERNAME = 0x020010;
    public static final int TYPE_PASSWORD = 0x020020;
    public static final int TYPE_USER_TOKEN = 0x020404;

    public static String getMessageValue(String message, int type) {
        String result = null;
        JSONObject json = ModeUtils.allModeToJson(message);
        JSONObject head = json.getJSONObject(GlobalConstant.HEAD);
        switch (type) {
            case TYPE_USERNAME:
                result = head.getString(GlobalConstant.USERNAME);
                break;
            case TYPE_PASSWORD:
                result = head.getString(GlobalConstant.PASSWORD);
                break;
            case TYPE_USER_TOKEN:
                result = head.getString(GlobalConstant.USER_TOKEN);
                break;
            default:
                break;
        }
        return result;
    }

    public static String resultMessage(String receiveMessage, String resultMessageCode, String
            resultMessage) {
        switch (ModeUtils.whichMode(receiveMessage)) {
            case ModeUtils.TYPE_JSON:
                Map<String, Object> message = new HashMap<>();
                Map<String, Object> headers = new HashMap<>();
                Map<String, Object> payload = new HashMap<>();
                Map<String, Object> errorInfo = new HashMap<>();
                errorInfo.put(GlobalConstant.ERROR_CODE, resultMessageCode);
                errorInfo.put(GlobalConstant.ERROR_MESSAGE, resultMessage);
                message.put(GlobalConstant.HEAD, headers);
                message.put(GlobalConstant.PAYLOAD, payload);
                message.put(GlobalConstant.ERROR_INFO, errorInfo);
                String response = JSON.toJSONString(message);
                receiveMessage = response;
                break;
            case ModeUtils.TYPE_XML:
                Document document = DocumentHelper.createDocument();
                Element messageEl = document.addElement("Message");
                Element headersEl = messageEl.addElement(GlobalConstant.HEAD);
                Element payloadEl = messageEl.addElement(GlobalConstant.PAYLOAD);
                Element errorInfoEl = messageEl.addElement(GlobalConstant.ERROR_INFO);
                Element errorCodeEl = errorInfoEl.addElement(GlobalConstant.ERROR_CODE);
                errorCodeEl.setText(resultMessageCode);
                Element errorMessageEl = errorInfoEl.addElement(GlobalConstant.ERROR_MESSAGE);
                errorMessageEl.setText(resultMessage);
                receiveMessage = document.asXML();
                break;
            case ModeUtils.TYPE_UN_DEFINITION:
                break;
            default:
                break;
        }
        return receiveMessage;
    }

}
