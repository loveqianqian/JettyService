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

import com.heren.turtle.entry.channel.handler.LoginAuthRespHandler;
import com.heren.turtle.entry.channel.handler.MessageReceiverHandler;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.handler.codec.serialization.ClassResolvers;
import io.netty.handler.codec.serialization.ObjectDecoder;
import io.netty.handler.codec.serialization.ObjectEncoder;

/**
 * Created by luoxiaoming on 16-6-13.
 */
public class MessageReceiveInitializer extends ChannelInitializer<Channel> {

    private final static int MAX_OBJECT_SIZE = 1024 * 1024;

    private boolean needToFilter;

    public MessageReceiveInitializer(boolean needToFilter) {
        this.needToFilter=needToFilter;
    }

    @Override
    protected void initChannel(Channel ch) throws Exception {
        ChannelPipeline pipeline = ch.pipeline();

        pipeline.addLast(new ObjectDecoder(MAX_OBJECT_SIZE, ClassResolvers.weakCachingConcurrentResolver(this.getClass()
                .getClassLoader())));
        pipeline.addLast(new ObjectEncoder());
        if (needToFilter) {
            pipeline.addLast(new LoginAuthRespHandler());
        }
        pipeline.addLast(new MessageReceiverHandler());
    }
}
