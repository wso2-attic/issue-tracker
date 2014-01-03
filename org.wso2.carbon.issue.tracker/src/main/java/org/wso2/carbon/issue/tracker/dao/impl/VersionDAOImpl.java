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

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.wso2.carbon.issue.tracker.bean.Version;
import org.wso2.carbon.issue.tracker.dao.VersionDAO;
import org.wso2.carbon.issue.tracker.util.DBConfiguration;
import org.wso2.carbon.issue.tracker.util.ISQLConstants;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class VersionDAOImpl implements VersionDAO {
    private static final Log log = LogFactory.getLog(VersionDAOImpl.class);

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean addVersionForProject(Version version, String projectKey, int tenantId) throws SQLException {
        Connection dbConnection = DBConfiguration.getDBConnection();
        PreparedStatement preparedStatement = null;
        String insertTableSQL = ISQLConstants.ADD_VERSION_FOR_PROJECT;

        try {
            dbConnection = DBConfiguration.getDBConnection();
            preparedStatement = dbConnection.prepareStatement(insertTableSQL);

            preparedStatement.setString(1, version.getVersion());
            preparedStatement.setString(2, projectKey);
            preparedStatement.setInt(3, tenantId);

            // execute insert SQL stetement
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            String msg = "Error while adding version to DB, version: " + version.getVersion();
            log.error(msg, e);
            throw e;
        } finally {
            if (preparedStatement != null) {
                preparedStatement.close();
            }
            if (dbConnection != null) {
                dbConnection.close();
            }
        }
        return true;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Version> getVersionListOfProjectByProjectKey(String projectKey, int tenantId) throws SQLException {
        Connection dbConnection = null;
        dbConnection = DBConfiguration.getDBConnection();

        String sql = ISQLConstants.GET_VERSION_OF_PROJECTS_BY_PROJECT_KEY;

        PreparedStatement preparedStatement = null;
        ResultSet rs = null;
        List<Version> versionList = new ArrayList<Version>();
        try {
            preparedStatement = dbConnection.prepareStatement(sql);
            preparedStatement.setString(1, projectKey);
            preparedStatement.setInt(2, tenantId);

            rs = preparedStatement.executeQuery();
            while (rs.next()) {
                Version version = new Version();
                version.setId(rs.getInt("VERSION_ID"));
                version.setVersion(rs.getString("VERSION"));
                version.setProjectId(rs.getInt("PROJECT_ID"));

                versionList.add(version);
            }

        } catch (SQLException e) {
            String msg = "Error while getting versions from DB, project key: " + projectKey;
            log.error(msg, e);
            throw e;
        } finally {
            if (preparedStatement != null) {
                preparedStatement.close();
            }
            if (dbConnection != null) {
                dbConnection.close();
            }
        }

        return versionList;

    }

}
