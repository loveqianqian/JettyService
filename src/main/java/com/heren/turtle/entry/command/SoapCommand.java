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
 * @create 2016-08-08 15:22.
 */
public class SoapCommand extends AbstractCommand {

    private Class<?> aClass;
    private boolean isFilter;
    private int port;
    private String ip;
    private String nameSuffix;

    public SoapCommand(Class<?> aClass, boolean isFilter, int port, String... args) {
        this.aClass = aClass;
        this.isFilter = isFilter;
        this.port = port;
        this.ip = args[0];
        this.nameSuffix = args[1];
    }

    @Override
    public void start() {
        Channel<Server> channel = new SoapChannel();
        Server status = channel.start(aClass, isFilter, port, ip, nameSuffix);
        status.start();
    }
}
