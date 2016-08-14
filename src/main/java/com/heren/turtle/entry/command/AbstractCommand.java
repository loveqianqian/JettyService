/*
 * Copyright ( c ) 2016 Heren Tianjin Corporation. All Rights Reserved.
 *
 * This software is the confidential and proprietary information of Heren Tianjin
 * Corporation ("Confidential Information").  You shall not disclose such
 * Confidential Information and shall use it only in accordance with the terms
 * of the license agreement you entered into with Heren Tianjin Corporation or a
 * Heren Tianjin authorized reseller (the "License Agreement").
 */

package com.heren.turtle.entry.command;

import com.heren.turtle.entry.util.XmlUtils;
import org.apache.cxf.endpoint.Server;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * com.heren.turtle.entry.command
 *
 * @author zhiwei
 * @create 2016-08-08 14:17.
 */
public abstract class AbstractCommand implements Command {

    public abstract void start();

    @Override
    public void startAll(String args) throws Exception {
        Document document = DocumentHelper.parseText(args);
        Element root = document.getRootElement();
        List elements = root.elements();
        List<Map<String, String>> eachElement = XmlUtils.getEachElement(elements);
        for (Map<String, String> map : eachElement) {
            String eleName = map.get("eleName");
            String name = map.get("name");
            String ip = map.get("ip");
            String port = map.get("port");
            String nameSuffix = map.get("nameSuffix");
            String status = map.get("status");
            String needToFilter = map.get("needToFilter");
            String method = map.get("method");
            String methodType = map.get("methodType");
            Class<?> aClass = Class.forName(name);
            if (Boolean.valueOf(status) && eleName.equals("Soap")) {
                Command command = new SoapCommand(aClass, Boolean.valueOf(needToFilter), Integer.parseInt(port), ip, nameSuffix);
                command.start();
            } else if (Boolean.valueOf(status) && eleName.equals("Rest")) {
                Command command = new RestCommand(aClass, Boolean.valueOf(needToFilter), Integer.parseInt(port), ip, nameSuffix);
                command.start();
            } else if (Boolean.valueOf(status) && eleName.equals("Netty")) {
                Command command = new NettyCommand(aClass, Boolean.valueOf(needToFilter), Integer.parseInt(port), method, methodType);
                command.start();
            }
        }
    }

}
