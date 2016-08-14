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

import com.heren.turtle.entry.security.SecurityRestFilter;
import org.apache.cxf.endpoint.Server;
import org.apache.cxf.jaxrs.JAXRSServerFactoryBean;

/**
 * com.heren.turtle.entry.command
 *
 * @author zhiwei
 * @create 2016-08-08 13:48.
 */
public class RestChannel implements Channel<Server> {

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
        JAXRSServerFactoryBean factory = new JAXRSServerFactoryBean();
        factory.setAddress(url);
        factory.setServiceBean(bean);
        assert bean != null;
        factory.setResourceClasses(bean.getClass());
        if (isFilter) {
            factory.getInInterceptors().add(new SecurityRestFilter());
        }
        return factory.create();
    }

}
