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
import org.wso2.carbon.issue.tracker.delegate.DAODeligate;
import org.wso2.carbon.issue.tracker.server.VersionService;
import org.wso2.carbon.issue.tracker.util.DBConfiguration;
import org.wso2.carbon.issue.tracker.util.IssueTrackerException;

import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

public class VersionServiceImpl implements VersionService {
    private static final Log log = LogFactory.getLog(VersionServiceImpl.class);

    public Response getVersionListOfProject(int projId) {
//        Connection dbConnection = null;
//        Statement statement = null;
//        dbConnection = DBConfiguration.getDBConnection();
//        String sql = "SELECT * FROM VERSION where Project_project_id = " + projId;
//        System.out.println("******sql:" + sql);
//        Statement stmt = null;
//        try {
//            stmt = dbConnection.createStatement();
//        } catch (SQLException e) {
//            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
//        }
//        ResultSet rs = null;
//        try {
//            rs = stmt.executeQuery(sql);
//        } catch (SQLException e) {
//            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
//        }
//
//        List<Version> versionList = new ArrayList<Version>();
//
//        int i = 0;
//        System.out.println("select statement sucessfully used");
//        try {
//            while (rs.next()) {
//                int versionId = rs.getInt("id");
//                String version = rs.getString("version");
//                int projectId = rs.getInt("Project_project_id");
//
//                Version v = new Version();
//                v.setProjectVersionId(versionId);
//                v.setProjectVersion(version);
//                v.setProjectId(projectId);
//
//                versionList.add(i, v);
//                i++;
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
//        }
//
//        return versionList;


         if(log.isDebugEnabled()){
            log.debug("Executing get Versions for project Id : projectID: " + projId);
        }

        System.out.println("Executing get Versions for project Id : projectID: " + projId);
        VersionDAO versionDAO = DAODeligate.getVersionInstance();
        List<Version> versionList = null;
        try {
            versionList = versionDAO.viewAllVersions(projId);
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
        Connection dbConnection = null;
                Statement statement = null;
                dbConnection = DBConfiguration.getDBConnection();
                try {
                           statement = dbConnection.createStatement();
                       } catch (SQLException e) {
                    try {
                        throw new IssueTrackerException("Error while creating SQL statement", e);
                    } catch (IssueTrackerException e1) {
                        e1.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
                    }
                }
                       String sql = "INSERT INTO User (user_id,name,Organization_org_id,Project_project_id) VALUES (11,\"microsoft-dev3\",4,7)";
                       try {
                           statement.executeUpdate(sql);
                       } catch (SQLException e) {
                           try {
                               throw new IssueTrackerException("Error while executing SQL statement", e);
                           } catch (IssueTrackerException e1) {
                               e1.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
                           }
                       }
        return Response.ok().entity(version).type(MediaType.APPLICATION_JSON).build();

    }

    public String sayHello() {
        System.out.println("Hellloooooo VERSION");
        return "hello VERSUION";
    }
}
