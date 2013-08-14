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

import com.google.gson.Gson;

import org.apache.log4j.Logger;
import org.wso2.carbon.issue.tracker.bean.Version;
import org.wso2.carbon.issue.tracker.dao.VersionDAO;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/versionService")
public class VersionService {

	Logger logger = Logger.getLogger(VersionService.class);

	@GET
	@Path("/project/{project_id}/")
	@Produces(MediaType.APPLICATION_JSON)
	public String getVersionListOfProject(
			@PathParam("project_id") String projectId) throws Exception {
		// return Response.status(200).entity("addUser is called, userAgent : "
		// + userAgent).build();
		StringBuilder b1 = new StringBuilder();
		VersionDAO versionDAO = new VersionDAO();

		List<Version> versionList = versionDAO.viewAllVersions(projectId);
		b1.append("{\"VersionList\":[\n");
		Gson gson1 = new Gson();
		String json1 = null;
		json1 = gson1.toJson(versionList);
		b1.append(json1);
		return b1.toString();
	}

	@POST
	@Path("/postVersion")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response addVersionToProject(Version version) throws Exception {

		logger.debug("----invoking addCustomer, Customer name is: "
				+ version.getProjectVersion());

		VersionDAO versionDAO = new VersionDAO();
		versionDAO.addVersionForProject(version);

		return Response.ok(version).build();
	}

	@GET
	@Path("/hello")
	public String sayHello() {
		System.out.println("Hellloooooo");
		return "hello";
	}
}
