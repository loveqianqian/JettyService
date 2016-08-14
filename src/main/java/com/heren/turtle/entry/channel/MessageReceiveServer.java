/*
 * Copyright ( c ) 2016 Heren Tianjin Corporation. All Rights Reserved.
 *
 * This software is the confidential and proprietary information of Heren Tianjin
 * Corporation ("Confidential Information").  You shall not disclose such
 * Confidential Information and shall use it only in accordance with the terms
 * of the license agreement you entered into with Heren Tianjin Corporation or a
 * Heren Tianjin authorized reseller (the "License Agreement").
 */

package com.heren.turtle.entry.channel;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

/**
 * <p>ProjectName:integration</p>
 * <p>Description:</p>
 *
 * @author:diaozhiwei
 * @data:2016/6/19
 */
public class MessageReceiveServer {

    private final static int MAX_OBJECT_SIZE = 1024 * 1024;

    public void bind(int port, boolean needToFilter) {
        System.out.println("netty start!");
        EventLoopGroup bossGroup = new NioEventLoopGroup(6);
        EventLoopGroup workGroup = new NioEventLoopGroup(12);
        try {
            ServerBootstrap b = new ServerBootstrap();
            b.group(bossGroup, workGroup)
                    .channel(NioServerSocketChannel.class)
                    .option(ChannelOption.SO_BACKLOG, 1024)
                    .childHandler(new MessageReceiveInitializer(needToFilter));
            //bound port,waiting for a successful synchronization
            ChannelFuture f = b.bind(port).sync();
            //waiting for the server listening port closed
            f.channel().closeFuture().sync();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            bossGroup.shutdownGracefully();
            workGroup.shutdownGracefully();
        }
    }

    public static void main(String[] args) throws Exception {
        int port = 8084;
        if (args != null && args.length > 0) {
            try {
                port = Integer.valueOf(args[0]);
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        }
        new MessageReceiveServer().bind(port, true);
    }
}
