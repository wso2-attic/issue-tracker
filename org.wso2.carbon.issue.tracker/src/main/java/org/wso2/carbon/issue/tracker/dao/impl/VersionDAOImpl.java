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
package org.wso2.carbon.issue.tracker.dao.impl;

import org.wso2.carbon.issue.tracker.bean.Version;
import org.wso2.carbon.issue.tracker.dao.VersionDAO;
import org.wso2.carbon.issue.tracker.util.DBConfiguration;
import org.wso2.carbon.issue.tracker.util.IssueTrackerException;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class VersionDAOImpl implements VersionDAO{
    @Override
    public boolean addVersionForProject(Version version) throws IssueTrackerException {
        Connection dbConnection = null;
                Statement statement = null;
                dbConnection = DBConfiguration.getDBConnection();
        PreparedStatement preparedStatement = null;
                try {
                           statement = dbConnection.createStatement();
                       } catch (SQLException e) {
                    try {
                        throw new IssueTrackerException("Error while creating SQL statement", e);
                    } catch (IssueTrackerException e1) {
                        e1.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
                    }
                }
            //INSERT INTO VERSION (VERSION,PROJECT_ID) VALUES ("5.0.0","1");

        String insertTableSQL = "INSERT INTO VERSION (VERSION,PROJECT_ID) VALUES (?, ?)";

                       try {
                           System.out.println("Version: " + version.getProjectVersion());
            System.out.println("project id: " + version.getProjectId());


            dbConnection = DBConfiguration.getDBConnection();
            preparedStatement = dbConnection.prepareStatement(insertTableSQL);

            preparedStatement.setString(1, version.getProjectVersion());
            preparedStatement.setInt(2, version.getProjectId());


            // execute insert SQL stetement
            preparedStatement.executeUpdate();

            System.out.println("Record is inserted into VERSION table!");
                       } catch (SQLException e) {
                           try {
                               throw new IssueTrackerException("Error while executing SQL statement", e);
                           } catch (IssueTrackerException e1) {
                               e1.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
                           }
                       }
        return true;
    }

    @Override
    public List<Version> viewAllVersions(int projId) throws IssueTrackerException, SQLException {
        Connection dbConnection = null;
        Statement statement = null;
        dbConnection = DBConfiguration.getDBConnection();
        String sql = "SELECT * FROM VERSION where PROJECT_ID = " + projId;
        System.out.println("******sql:" + sql);
        Statement stmt = null;
        try {
            stmt = dbConnection.createStatement();
        } catch (SQLException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        ResultSet rs = null;
        try {
            rs = stmt.executeQuery(sql);
        } catch (SQLException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }

        List<Version> versionList = new ArrayList<Version>();

        int i = 0;
        System.out.println("select statement sucessfully used");
        try {
            while (rs.next()) {
                int versionId = rs.getInt("VERSION_ID");
                String version = rs.getString("VERSION");
                int projectId = rs.getInt("PROJECT_ID");

                Version v = new Version();
                v.setProjectVersionId(versionId);
                v.setProjectVersion(version);
                v.setProjectId(projectId);

                versionList.add(i, v);
                i++;
            }
        } catch (SQLException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }

        return versionList;

    }

}
