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
import com.heren.turtle.entry.constant.ExceptionConstant;
import com.heren.turtle.entry.util.LoadUtils;
import io.netty.channel.*;

import java.net.InetSocketAddress;
import java.util.List;
import java.util.Map;


/**
 * Created by zhiwei on 2016/6/15.
 */
public class LoginAuthRespHandler extends ChannelHandlerAdapter {

    private String loginResp = "";

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        String message = (String) msg;
        System.out.println(message);
        String username = LoadUtils.getMessageValue(message, LoadUtils.TYPE_USERNAME);
        String password = LoadUtils.getMessageValue(message, LoadUtils.TYPE_PASSWORD);
        String token = LoadUtils.getMessageValue(message, LoadUtils.TYPE_USER_TOKEN);
        List<String> refuseIPAddress = Any.getRefuseAddress();
        InetSocketAddress address = (InetSocketAddress) ctx.channel().remoteAddress();
        String remoteAddress = address.getAddress().getHostAddress();
        boolean refuseStatus = !refuseIPAddress.contains(remoteAddress);
        if (refuseStatus) {
            if (token.equals(Any.getToken())) {
                Map<String, String> useInfo = Any.getUseInfo();
                boolean usernameContain = useInfo.containsKey(username);
                if (usernameContain) {
                    if (!useInfo.get(username).equals(password)) {
                        Map<String, String> newUseInfo = Any.getUseInfo();
                        if (newUseInfo == null || !newUseInfo.containsKey(username) || !newUseInfo.get
                                (username).equals(password)) {
                            Any.recordRefuseIp(remoteAddress);
                            loginResp = LoadUtils.resultMessage(message, ExceptionConstant
                                    .INCORRECT_PASSWORD, "exception,incorrect password");
                            ctx.writeAndFlush(loginResp);
                        }
                    }
                } else {
                    loginResp = LoadUtils.resultMessage(message, ExceptionConstant.INCORRECT_USERNAME,
                            "exception,incorrect username");
                    ctx.writeAndFlush(loginResp);
                }
            } else {
                loginResp = LoadUtils.resultMessage(message, ExceptionConstant.INCORRECT_TOKEN, "exception," +
                        "incorrect token");
                ctx.writeAndFlush(loginResp);
            }
        } else {
            loginResp = LoadUtils.resultMessage(message, ExceptionConstant.TOO_MANY_TIME, "wrong too many " +
                    "times ,this is ip address(" + remoteAddress + ") not allow to login");
            ctx.writeAndFlush(loginResp);
        }
        ctx.fireChannelRead(msg);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.close();
        ctx.fireExceptionCaught(cause);
    }
}
