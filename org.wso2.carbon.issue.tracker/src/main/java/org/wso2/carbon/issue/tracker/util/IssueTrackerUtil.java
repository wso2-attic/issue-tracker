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
package org.wso2.carbon.issue.tracker.util;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class IssueTrackerUtil {
    private static final Log log = LogFactory.getLog(IssueTrackerUtil.class);
    static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    static final String DB_URL = "jdbc:mysql://localhost:3306/issueTrackerDb22";

    //  Database credentials
    static final String USER = "root";
    static final String PASS = "root";


    public static Connection getConnection() throws IssueTrackerException {
        Connection conn;
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new IssueTrackerException("Driver class not found", e);
        }
        if (log.isDebugEnabled()){
            log.debug("Connecting to a selected database...");
        }
        try {
            conn = DriverManager.getConnection(DB_URL, USER, PASS);
        } catch (SQLException e) {
            throw new IssueTrackerException("Could not establish connection", e);
        }
        if(log.isDebugEnabled()){
            log.debug("Connected database successfully...");
        }
        return conn;
    }

    public static void executeQuery(Connection dbConnection, String query) throws IssueTrackerException{
                    Statement stmt = null;
        try {
            stmt = dbConnection.createStatement();
        } catch (SQLException e) {
            throw new IssueTrackerException("Error while creating SQL statement", e);
        }
        String sql = "INSERT INTO User (user_id,name,Organization_org_id,Project_project_id) VALUES (11,\"microsoft-dev3\",4,7)";
        try {
            stmt.executeUpdate(sql);
        } catch (SQLException e) {
             throw new IssueTrackerException("Error while executing SQL statement", e);
        }

        if (log.isDebugEnabled()){
            log.debug("Inserted records into the table...");
        }
    }

}
