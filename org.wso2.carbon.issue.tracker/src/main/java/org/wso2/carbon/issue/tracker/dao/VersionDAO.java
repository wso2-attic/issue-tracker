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
package org.wso2.carbon.issue.tracker.dao;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.wso2.carbon.issue.tracker.bean.Version;
import org.wso2.carbon.issue.tracker.util.DBConfiguration;
import org.wso2.carbon.issue.tracker.util.IssueTrackerException;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class VersionDAO {

    private static final Log log = LogFactory.getLog(VersionDAO.class);

    public String addVersionForProject(Version version) throws IssueTrackerException {
         Connection dbConnection = null;
        Statement statement = null;
        dbConnection = DBConfiguration.getDBConnection();
        try {
                   statement = dbConnection.createStatement();
               } catch (SQLException e) {
                   throw new IssueTrackerException("Error while creating SQL statement", e);
               }
               String sql = "INSERT INTO User (user_id,name,Organization_org_id,Project_project_id) VALUES (11,\"microsoft-dev3\",4,7)";
               try {
                   statement.executeUpdate(sql);
               } catch (SQLException e) {
                    throw new IssueTrackerException("Error while executing SQL statement", e);
               }

////        Connection conn;
////
////        conn = IssueTrackerUtil.getConnection();
//        String sql = "INSERT INTO Version (id,version,Project_project_id) " +
//                  "VALUES('"+ version.getProjectVersionId()+"','" + version.getProjectVersion()+"','"+version.getProjectId()+")";
//        IssueTrackerUtil.executeQuery(conn, sql);
        return null;
    }

    public List<Version> viewAllVersions(String projId) throws IssueTrackerException, SQLException {
         Connection dbConnection = null;
        Statement statement = null;
        dbConnection = DBConfiguration.getDBConnection();
        String sql = "SELECT * FROM VERSION where Project_project_id = " +projId;
        System.out.println("******sql:"+sql);
         Statement stmt= dbConnection.createStatement();
        ResultSet rs = stmt.executeQuery(sql);

        List<Version> versionList = new ArrayList<Version>();

        int i = 0;
	            System.out.println("select statement sucessfully used");
	            while (rs.next()) {
	                int versionId= rs.getInt("id");
	                String version= rs.getString("version");
	                int projectId= rs.getInt("Project_project_id");

                    Version v = new Version();
                    v.setProjectVersionId(versionId);
                    v.setProjectVersion(version);
                    v.setProjectId(projectId);

                    versionList.add(i,v);
                    i++;
	                }

//             for(int a=0; a<versionList.size(); a++){
//                 System.out.println(versionList.);
//             }
        //IssueTrackerUtil.executeQuery(conn,sql);
        return versionList;
    }

    private static void handleException(String msg, Throwable t) throws IssueTrackerException {
        log.error(msg, t);
        throw new IssueTrackerException(msg, t);
    }

}
