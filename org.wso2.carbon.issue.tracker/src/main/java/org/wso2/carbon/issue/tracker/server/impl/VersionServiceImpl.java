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
package org.wso2.carbon.issue.tracker.server.impl;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.wso2.carbon.issue.tracker.bean.Version;
import org.wso2.carbon.issue.tracker.dao.VersionDAO;
import org.wso2.carbon.issue.tracker.delegate.DAODelegate;
import org.wso2.carbon.issue.tracker.server.VersionService;
import org.wso2.carbon.issue.tracker.util.IssueTrackerException;

import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.sql.SQLException;
import java.util.List;

public class VersionServiceImpl implements VersionService {
    private static final Log log = LogFactory.getLog(VersionServiceImpl.class);

    public Response getVersionListOfProjectByProjectId(int projId) {
         if(log.isDebugEnabled()){
            log.debug("Executing get Versions for project Id : projectID: " + projId);
        }

        System.out.println("Executing get Versions for project Id : projectID: " + projId);
        VersionDAO versionDAO = DAODelegate.getVersionInstance();
        List<Version> versionList = null;
        try {
            versionList = versionDAO.getVersionListOfProjectByProjectId(projId);
        } catch (SQLException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).type(MediaType.APPLICATION_JSON_TYPE).build();
        } catch (IssueTrackerException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }

        GenericEntity<List<Version>> entity = new GenericEntity<List<Version>>(versionList){} ;
        return Response.ok().entity(entity).type(MediaType.APPLICATION_JSON_TYPE).build();
    }

    public Response addVersionToProject(Version version) {

         if(log.isDebugEnabled()){
            log.debug("Executing post for project version, project version: " + version.getProjectVersion());
        }
        VersionDAO versioDAO = DAODelegate.getVersionInstance();
        try {
            versioDAO.addVersionForProject(version);
            return Response.ok().entity(version).type(MediaType.APPLICATION_JSON).build();
        } catch (IssueTrackerException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            return Response.notModified().type(MediaType.APPLICATION_JSON_TYPE).build();
        }


    }

    public String sayHello() {
        return "hello VERSION";
    }
}
