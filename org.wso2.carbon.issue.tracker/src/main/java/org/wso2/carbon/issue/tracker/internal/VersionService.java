/*
 *  Licensed to the Apache Software Foundation (ASF) under one
 *  or more contributor license agreements.  See the NOTICE file
 *  distributed with this work for additional information
 *  regarding copyright ownership.  The ASF licenses this file
 *  to you under the Apache License, Version 2.0 (the
 *  "License"); you may not use this file except in compliance
 *  with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing,
 *  software distributed under the License is distributed on an
 *   * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 *  KIND, either express or implied.  See the License for the
 *  specific language governing permissions and limitations
 *  under the License.
 */
package org.wso2.carbon.issue.tracker.internal;

import org.wso2.carbon.issue.tracker.bean.Version;
import org.wso2.carbon.issue.tracker.dao.VersionDAO;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/versionService")
public class VersionService {

    @GET
    @Path("/project/{id}/")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Version> getVersionListOfProject(@PathParam("project_id") String projectId) throws Exception {
        // return Response.status(200).entity("addUser is called, userAgent : " + userAgent).build();
        return null;
    }

    @POST
    @Path("/postVersion")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response addVersionToProject(Version version) throws Exception{

        System.out.println("----invoking addCustomer, Customer name is: " + version.getProjectVersion());

        VersionDAO versionDAO = new VersionDAO();
        versionDAO.addVersionForProject(version);

        return Response.ok(version).build();
    }
}
