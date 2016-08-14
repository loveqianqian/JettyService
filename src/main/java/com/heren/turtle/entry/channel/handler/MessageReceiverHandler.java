/*
 * Copyright ( c ) 2016 Heren Tianjin Corporation. All Rights Reserved.
 *
 * This software is the confidential and proprietary information of Heren Tianjin
 * Corporation ("Confidential Information").  You shall not disclose such
 * Confidential Information and shall use it only in accordance with the terms
 * of the license agreement you entered into with Heren Tianjin Corporation or a
 * Heren Tianjin authorized reseller (the "License Agreement").
 */

package com.heren.turtle.entry.channel.handler;

import com.heren.turtle.entry.any.Any;
import com.heren.turtle.entry.any.TestBean;
import com.heren.turtle.entry.core.ClientCheck;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import org.dom4j.DocumentException;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * <p>ProjectName:integration</p>
 * <p>Description:</p>
 *
 * @author:diaozhiwei
 * @data:2016/6/19
 */
public class MessageReceiverHandler extends ChannelHandlerAdapter {


    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        String params = (String) msg;
        ClientCheck clientCheck = new ClientCheck();
        List<String> lackOfStrings = null;
        try {
            lackOfStrings = clientCheck.checkLegitimate(params, Any.getTemplate("0000"));
        } catch (DocumentException e) {
            e.printStackTrace();
        }
        if (lackOfStrings != null && !lackOfStrings.isEmpty()) {
            StringBuffer sb = new StringBuffer();
            sb.append("[");
            for (String s : lackOfStrings) {
                sb.append(s);
                sb.append(",");
            }
            sb.append("]");
            ctx.writeAndFlush("some element of this message is lack of." + sb.toString());
            try {
                throw new RuntimeException("some element of this message is lack of." + sb.toString());
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("do something else");
            ctx.writeAndFlush("success");
        }

    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        ctx.flush();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.close();
    }
}
