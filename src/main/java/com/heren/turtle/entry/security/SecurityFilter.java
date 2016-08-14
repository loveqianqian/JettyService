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
import org.apache.cxf.binding.soap.SoapHeader;
import org.apache.cxf.binding.soap.SoapMessage;
import org.apache.cxf.headers.Header;
import org.apache.cxf.helpers.XMLUtils;
import org.apache.cxf.interceptor.Fault;
import org.apache.cxf.phase.AbstractPhaseInterceptor;
import org.apache.cxf.phase.Phase;
import org.apache.cxf.transport.http.AbstractHTTPDestination;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * com.myloverqian.security SecurityFilter
 *
 * @author zhiwei
 * @create 2016-07-19 15:52.
 */
public class SecurityFilter extends AbstractPhaseInterceptor<SoapMessage> {

    public SecurityFilter() {
        super(Phase.PRE_INVOKE);
    }

    @Override
    public void handleMessage(SoapMessage message) throws Fault {
        HttpServletRequest request = (HttpServletRequest) message.get(AbstractHTTPDestination.HTTP_REQUEST);
        List<String> refuseIPAddress = Any.getRefuseAddress();
        String remoteAddress = request.getRemoteAddr();
        boolean refuseStatus = !refuseIPAddress.contains(remoteAddress);
        if (refuseStatus) {
            List<Header> headers = message.getHeaders();
            if (headers == null || headers.isEmpty()) {
                throwExceptionByMessage("this soap message do not have headers! not allow to access");
            }
            for (Header header : headers) {
                SoapHeader soapHeader = (SoapHeader) header;
                Element element = (Element) soapHeader.getObject();
                XMLUtils.printDOM(element);
                NodeList nameList = element.getElementsByTagName("tns:name");
                String name = nameList.item(0).getTextContent();
                NodeList passList = element.getElementsByTagName("tns:pass");
                String pwd = passList.item(0).getTextContent();
                NodeList tokenList = element.getElementsByTagName("tns:token");
                String token = tokenList.item(0).getTextContent();
                if (token != null && token.equals(Any.getToken())) {
                    Map<String, String> useInfo = Any.getUseInfo();
                    boolean usernameContain = name != null && useInfo.containsKey(name);
                    if (usernameContain) {
                        if (pwd == null || !useInfo.get(name).equals(pwd)) {
                            Map<String, String> newUseInfo = Any.getUseInfo();
                            if (newUseInfo == null || !newUseInfo.containsKey(name) || !newUseInfo.get
                                    (name).equals(pwd)) {
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
            }
        } else {
            throwExceptionByMessage("wrong too many times ,this is ip address(" + remoteAddress + ") not " +
                    "allow to login");
        }
    }

    private void throwExceptionByMessage(String msg) {
        throw new Fault(new IllegalAccessException(msg));
    }
}
