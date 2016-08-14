/*
 * Copyright ( c ) 2016 Heren Tianjin Corporation. All Rights Reserved.
 *
 * This software is the confidential and proprietary information of Heren Tianjin
 * Corporation ("Confidential Information").  You shall not disclose such
 * Confidential Information and shall use it only in accordance with the terms
 * of the license agreement you entered into with Heren Tianjin Corporation or a
 * Heren Tianjin authorized reseller (the "License Agreement").
 */
package com.heren.turtle.entry.service;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;

/**
 * com.heren.turtle.entry.service
 *
 * @author zhiwei
 * @create 2016-07-19 10:23.
 */
@WebService(targetNamespace = "http://service.entry.turtle.heren.com/", name = "soapService",
        portName = "soapServicePort", serviceName = "SoapService")
public interface ISoapService {

    @WebMethod
    String invoke(@WebParam(name = "params", targetNamespace = "http://service.entry.turtle.heren.com") String params);

}
