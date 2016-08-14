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

/**
 * com.heren.turtle.entry.command
 *
 * @author zhiwei
 * @create 2016-08-08 14:12.
 */
public interface Command {

    void start();

    void startAll(String xmlString) throws Exception;

}
