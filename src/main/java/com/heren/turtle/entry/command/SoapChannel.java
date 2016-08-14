/*
 * Copyright ( c ) 2016 Heren Tianjin Corporation. All Rights Reserved.
 *
 * This software is the confidential and proprietary information of Heren Tianjin
 * Corporation ("Confidential Information").  You shall not disclose such
 * Confidential Information and shall use it only in accordance with the terms
 * of the license agreement you entered into with Heren Tianjin Corporation or a
 * Heren Tianjin authorized reseller (the "License Agreement").
 */

package com.heren.turtle.entry.command;

import com.heren.turtle.entry.security.SecurityFilter;
import org.apache.cxf.endpoint.Server;
import org.apache.cxf.jaxws.JaxWsServerFactoryBean;

/**
 * com.heren.turtle.entry.command JettyService
 *
 * @author zhiwei
 * @create 2016-08-08 13:48.
 */
public class SoapChannel implements Channel<Server> {

    /**
     * @param aClass
     * @param isFilter
     * @param port
     * @param args     args[0]-ip,args[1]-nameSuffix
     * @return
     */
    @Override
    public Server start(Class<?> aClass, boolean isFilter, int port, String... args) {
        String url = String.format(channelUrl, args[0], port, args[1]);
        Object bean = null;
        try {
            bean = aClass.newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }
        JaxWsServerFactoryBean factory = new JaxWsServerFactoryBean();
        factory.setAddress(url);
        factory.setServiceBean(bean);
        if (isFilter) {
            factory.getInInterceptors().add(new SecurityFilter());
        }
        return factory.create();
    }
}
