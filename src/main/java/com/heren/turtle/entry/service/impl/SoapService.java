/*
 * Copyright ( c ) 2016 Heren Tianjin Corporation. All Rights Reserved.
 *
 * This software is the confidential and proprietary information of Heren Tianjin
 * Corporation ("Confidential Information").  You shall not disclose such
 * Confidential Information and shall use it only in accordance with the terms
 * of the license agreement you entered into with Heren Tianjin Corporation or a
 * Heren Tianjin authorized reseller (the "License Agreement").
 */

package com.heren.turtle.entry.service.impl;

import com.heren.turtle.entry.any.Any;
import com.heren.turtle.entry.core.ClientCheck;
import com.heren.turtle.entry.service.ISoapService;
import org.dom4j.DocumentException;
import org.springframework.stereotype.Component;

import javax.jws.WebService;
import java.util.List;

/**
 * com.heren.turtle.entry.service.impl
 *
 * @author zhiwei
 * @create 2016-07-19 10:23.
 */
@WebService
@Component("soapService")
public class SoapService implements ISoapService {

    @Override
    public String invoke(String params) {
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
            throw new RuntimeException("some element of this message is lack of." + sb.toString());
        } else {
            System.out.println("do something else");
            return "soap";
        }
    }


}
