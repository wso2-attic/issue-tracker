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

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class VersionDAOImpl implements VersionDAO{
    @Override
    public String addVersionForProject(Version version) throws IssueTrackerException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
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
