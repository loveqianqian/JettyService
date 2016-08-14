/*
 * Copyright ( c ) 2016 Heren Tianjin Corporation. All Rights Reserved.
 *
 * This software is the confidential and proprietary information of Heren Tianjin
 * Corporation ("Confidential Information").  You shall not disclose such
 * Confidential Information and shall use it only in accordance with the terms
 * of the license agreement you entered into with Heren Tianjin Corporation or a
 * Heren Tianjin authorized reseller (the "License Agreement").
 */

package com.heren.turtle.entry.security;

import com.heren.turtle.entry.any.Any;
import org.apache.cxf.interceptor.Fault;
import org.apache.cxf.message.Message;
import org.apache.cxf.phase.AbstractPhaseInterceptor;
import org.apache.cxf.phase.Phase;
import org.apache.cxf.transport.http.AbstractHTTPDestination;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;

/**
 * com.myloverqian.security SecurityFilter
 *
 * @author zhiwei
 * @create 2016-07-19 15:52.
 */
public class SecurityRestFilter extends AbstractPhaseInterceptor {

    public SecurityRestFilter() {
        super(Phase.PRE_INVOKE);
    }

    private void throwExceptionByMessage(String msg) {
        throw new Fault(new IllegalAccessException(msg));
    }

    @Override
    public void handleMessage(Message message) throws Fault {
        HttpServletRequest request = (HttpServletRequest) message.get(AbstractHTTPDestination.HTTP_REQUEST);
        HttpSession session = request.getSession();
        String userName = (String) session.getAttribute("tnsName");
        String password = (String) session.getAttribute("tnsPassword");
        String token = (String) session.getAttribute("tnsToken");
        List<String> refuseIPAddress = Any.getRefuseAddress();
        String remoteAddress = request.getRemoteAddr();
        boolean refuseStatus = !refuseIPAddress.contains(remoteAddress);
        if (refuseStatus) {
            if (token != null && token.equals(Any.getToken())) {
                Map<String, String> useInfo = Any.getUseInfo();
                boolean usernameContain = userName != null && useInfo.containsKey(userName);
                if (usernameContain) {
                    if (!useInfo.get(userName).equals(password)) {
                        Map<String, String> newUseInfo = Any.getUseInfo();
                        if (password == null && newUseInfo == null || !newUseInfo.containsKey(userName) ||
                                !newUseInfo.get(userName).equals(password)) {
                            Any.recordRefuseIp(remoteAddress);
                            throwExceptionByMessage("exception,incorrect password");
                        }
                    }
                } else {
                    throwExceptionByMessage("exception,incorrect username");
                }
            } else {
                throwExceptionByMessage("exception,incorrect token");
            }
        } else {
            throwExceptionByMessage("wrong too many times ,this is ip address(" + remoteAddress + ") not " +
                    "allow to login");
        }
    }
}
