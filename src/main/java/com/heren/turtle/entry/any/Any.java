/*
 * Copyright ( c ) 2016 Heren Tianjin Corporation. All Rights Reserved.
 *
 * This software is the confidential and proprietary information of Heren Tianjin
 * Corporation ("Confidential Information").  You shall not disclose such
 * Confidential Information and shall use it only in accordance with the terms
 * of the license agreement you entered into with Heren Tianjin Corporation or a
 * Heren Tianjin authorized reseller (the "License Agreement").
 */

package com.heren.turtle.entry.any;

import com.heren.turtle.entry.util.XmlUtils;
import com.heren.turtle.entry.core.TemplateReader;

import java.util.*;

/**
 * com.myloverqian.any JettyService
 *
 * @author zhiwei
 * @create 2016-07-20 16:41.
 */
public class Any {

    public static TemplateReader.Template getTemplate(String msg) {
        TemplateReader.Template template = null;
        switch (msg) {
            case "0000":
                TemplateReader reader = new TemplateReader();
                try {
                    reader.instanceTemplate(XmlUtils.getFileDocument("example.xml"));
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
        }
        return template;
    }

    public static List<String> getRefuseAddress() {
        return Arrays.asList("10.0.0.70", "192.168.8.41");
    }

    public static void recordRefuseIp(String ip) {
        System.out.println("record refuse address" + ip);
    }

    public static String getToken() {
        return "a1e9a3be-d86b-4c22-a261-300b7fad6e73";
    }

    public static Map<String, String> getUseInfo() {
        Map<String, String> result = new HashMap<>();
        result.put("joke", "fruit");
        return result;
    }

}
