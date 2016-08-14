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

import io.netty.channel.Channel;
import org.springframework.util.Assert;

/**
 * Created by luoxiaoming on 16-6-13.
 */
public class MessageSender {

    private static volatile Channel channel;

    private static volatile boolean alive = false;


    public static void initialized(Channel channel) {
        Assert.notNull(channel, "the channel is null!");
        MessageSender.channel = channel;
        alive = true;
    }

    public static Channel getChannel() {
        return channel;
    }
}
