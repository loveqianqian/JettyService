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

import org.apache.cxf.endpoint.Server;

/**
 * com.heren.turtle.entry.command
 *
 * @author zhiwei
 * @create 2016-08-08 15:26.
 */
public class RestCommand extends AbstractCommand {

    private Class<?> aClass;
    private boolean isFilter;
    private int port;
    private String ip;
    private String nameSuffix;

    public RestCommand(Class<?> aClass, boolean isFilter, int port, String ip, String nameSuffix) {
        this.aClass = aClass;
        this.isFilter = isFilter;
        this.port = port;
        this.ip = ip;
        this.nameSuffix = nameSuffix;
    }

    @Override
    public void start() {
        Channel<Server> channel = new RestChannel();
        Server status = channel.start(aClass, isFilter, port, ip, nameSuffix);
        status.start();
    }
}
