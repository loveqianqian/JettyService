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

/**
 * com.heren.turtle.entry.command
 *
 * @author zhiwei
 * @create 2016-08-08 15:13.
 */
public class NettyCommand extends AbstractCommand {

    private Class<?> aClass;
    private boolean isFilter;
    private int port;
    private String methodType;
    private String method;

    public NettyCommand(Class<?> aClass, boolean isFilter, int port, String... args) {
        this.aClass = aClass;
        this.isFilter = isFilter;
        this.port = port;
        this.method = args[0];
        this.methodType = args[1];
    }

    @Override
    public void start() {
        Channel<Thread> channel = new NettyChannel();
        Thread status = channel.start(aClass, isFilter, port, method, methodType);
        status.start();
    }
}
