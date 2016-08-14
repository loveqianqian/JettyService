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

import java.lang.reflect.Method;

/**
 * com.heren.turtle.entry.command
 *
 * @author zhiwei
 * @create 2016-08-08 13:49.
 */
public class NettyChannel implements Channel<Thread> {

    private static final String typeInt = "int";
    private static final String typeBoolean = "boolean";
    private static final String typeString = "string";

    /**
     * @param aClass
     * @param isFilter
     * @param port
     * @param args     args[0]-method,args[1]-methodType
     * @return
     */
    @Override
    public Thread start(Class<?> aClass, boolean isFilter, int port, String... args) {
        String method = args[0];
        String methodType = args[1];
        return new Thread(() -> {
            Object obj = null;
            try {
                obj = aClass.newInstance();
                String[] ss = methodType.split(",");
                Class[] cls = new Class[ss.length];
                for (int i = 0; i < ss.length; i++) {
                    String s = ss[i];
                    switch (s) {
                        case typeInt:
                            cls[i] = int.class;
                            break;
                        case typeBoolean:
                            cls[i] = boolean.class;
                            break;
                        case typeString:
                            cls[i] = String.class;
                            break;
                    }
                }
                Method bind = aClass.getMethod(method, cls);
                bind.invoke(obj, port, isFilter);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

}
