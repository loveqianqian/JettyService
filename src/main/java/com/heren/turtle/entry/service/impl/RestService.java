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

import com.heren.turtle.entry.service.IRestService;
import com.heren.turtle.entry.any.Any;
import com.heren.turtle.entry.any.TestBean;
import com.heren.turtle.entry.core.ClientCheck;
import org.dom4j.DocumentException;

import java.util.List;

/**
 * <p>ProjectName:JettyService</p>
 * <p>Description:</p>
 *
 * @author:diaozhiwei
 * @data:2016/8/2
 */
public class RestService implements IRestService {

    @Override
    public TestBean queryRest(String params) {
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
            return new TestBean("test", "test");
        }
    }

}
