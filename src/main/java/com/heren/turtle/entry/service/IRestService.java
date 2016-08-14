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

import com.heren.turtle.entry.any.TestBean;

import javax.jws.WebService;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

/**
 * <p>ProjectName:JettyService</p>
 * <p>Description:</p>
 *
 * @author:diaozhiwei
 * @data:2016/8/2
 */
@WebService
@Path("/rest")
public interface IRestService {

    @GET
    @Path("/query/{params}")
    @Produces(value = {MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    TestBean queryRest(@PathParam("params") String params);

}
