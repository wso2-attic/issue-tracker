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

import java.text.DateFormat;
import java.text.SimpleDateFormat;

/**
 * SQL Queries
 */
public interface ISQLConstants {

    // COMMENT
    String GET_COMMENTS_FOR_ISSUE = "SELECT ID, DESCRIPTION, CREATED_TIME, UPDATED_TIME, CREATOR FROM COMMENT WHERE ISSUE_ID = ? AND ORGANIZATION_ID = ? " +
            "ORDER BY ID ASC";

    String ADD_COMMENT_FOR_ISSUE = "INSERT INTO COMMENT (DESCRIPTION, CREATED_TIME, UPDATED_TIME, CREATOR, ISSUE_ID, ORGANIZATION_ID) SELECT ?, ?, ?, ?, ISSUE_ID, ? " +
            "FROM ISSUE WHERE PKEY = ?";

    String UPDATE_COMMENT = "UPDATE COMMENT c SET c.DESCRIPTION = ?, c.UPDATED_TIME = ?  WHERE c.ID=? AND c.CREATOR = ? AND c.ORGANIZATION_ID = ?";

    String DELETE_COMMENT_BY_COMMENT_ID = "DELETE c FROM COMMENT c WHERE c.ORGANIZATION_ID = ? AND c.ID = ?";


    // ISSUE
    String ADD_ISSUE = "INSERT INTO ISSUE(PROJECT_ID,SUMMARY,DESCRIPTION, " +
            "ISSUE_TYPE,PRIORITY,OWNER,STATUS,ASSIGNEE,VERSION_ID, " +
            "CREATED_TIME,SEVERITY, ORGANIZATION_ID) SELECT p.PROJECT_ID,?,?,?,?,?,?,?,?,?,?,p.ORGANIZATION_ID FROM PROJECT p WHERE p.PROJECT_KEY=? AND p.ORGANIZATION_ID=?";

    String UPDATE_ISSUE_PKEY = "UPDATE ISSUE SET PKEY = ? WHERE ISSUE_ID = ?";

    String UPDATE_ISSUE = "UPDATE ISSUE SET DESCRIPTION = ?, " +
            "ISSUE_TYPE = ?, PRIORITY = ?, STATUS = ?, ASSIGNEE = ?, VERSION_ID = ?, " +
            "UPDATED_TIME = ?, SEVERITY = ? WHERE PKEY = ? AND OWNER = ? AND ORGANIZATION_ID = ?";

    String GET_ISSUE_BY_KEY = "SELECT p.PROJECT_KEY, p.PROJECT_NAME, i.ISSUE_ID, i.PKEY, i.PROJECT_ID, i.SUMMARY, i.DESCRIPTION, i.ISSUE_TYPE, i.PRIORITY, i.OWNER, " +
            "i.STATUS, i.ASSIGNEE, i.VERSION_ID, i.CREATED_TIME, i.UPDATED_TIME, i.SEVERITY FROM ISSUE i INNER JOIN PROJECT p ON (i.PROJECT_ID=p.PROJECT_ID)  " +
            "WHERE i.PKEY = ? AND i.ORGANIZATION_ID = ?";

    String GET_ISSUE_BY_ID = "SELECT ISSUE_ID, PKEY, PROJECT_ID, SUMMARY, DESCRIPTION, ISSUE_TYPE, PRIORITY, OWNER, STATUS, ASSIGNEE, VERSION_ID, CREATED_TIME, " +
            "UPDATED_TIME,SEVERITY FROM ISSUE WHERE ISSUE_ID = ? AND ORGANIZATION_ID = ?";

    String GET_ALL_ISSUE_OF_PROJECT = "SELECT v.VERSION, p.PROJECT_NAME, i.ISSUE_ID, i.PKEY, i.PROJECT_ID, i.SUMMARY, i.DESCRIPTION, i.ISSUE_TYPE, i.PRIORITY, " +
            "i.OWNER, i.STATUS, i.ASSIGNEE, i.VERSION_ID, i.CREATED_TIME, i.UPDATED_TIME, i.SEVERITY " +
            "FROM ISSUE i INNER JOIN PROJECT p ON i.PROJECT_ID = p.PROJECT_ID " +
            "LEFT OUTER JOIN VERSION v ON i.VERSION_ID = v.VERSION_ID " +
            "WHERE p.PROJECT_KEY = ifnull(?, p.PROJECT_KEY) AND p.ORGANIZATION_ID = ?";

    // PROJECT
    String ADD_PROJECT = "INSERT INTO PROJECT (PROJECT_NAME,OWNER,DESCRIPTION,ORGANIZATION_ID,PROJECT_KEY) VALUES (?,?,?,?,?)";

    String UPDATE_PROJECT = "UPDATE PROJECT SET PROJECT_NAME = ?, OWNER = ?, DESCRIPTION = ? WHERE PROJECT_KEY = ? AND ORGANIZATION_ID = ?";

    String GET_PROJECT = "SELECT PROJECT_ID, PROJECT_NAME, OWNER, DESCRIPTION, ORGANIZATION_ID FROM PROJECT WHERE PROJECT_KEY = ? AND ORGANIZATION_ID=?";

    String GET_PROJECTS_BY_ORGANIZATION_ID = "SELECT PROJECT_ID, PROJECT_NAME, OWNER, DESCRIPTION, ORGANIZATION_ID, PROJECT_KEY FROM PROJECT WHERE ORGANIZATION_ID = ?";

    String ADD_VERSION_FOR_PROJECT = "INSERT INTO VERSION (VERSION, PROJECT_ID) SELECT ?, p.PROJECT_ID FROM PROJECT p WHERE p.PROJECT_KEY=? AND p.ORGANIZATION_ID=?";

    String GET_VERSION_OF_PROJECTS_BY_PROJECT_KEY = "SELECT v.VERSION_ID, v.VERSION, v.PROJECT_ID FROM VERSION v INNER JOIN PROJECT p ON v.PROJECT_ID = p.PROJECT_ID " +
            "WHERE p.PROJECT_KEY=? AND p.ORGANIZATION_ID=?";

    // SEARCH
    String SEARCH_ISSUE = "SELECT p.PROJECT_KEY, p.PROJECT_ID, p.PROJECT_NAME, v.VERSION, i.PKEY, i.SUMMARY, i.ISSUE_TYPE, i.PRIORITY, " +
            "i.OWNER, i.STATUS, i.ASSIGNEE, i.SEVERITY FROM ISSUE i " +
            "INNER JOIN PROJECT p " +
            "ON p.PROJECT_ID = i.PROJECT_ID " +
            "LEFT OUTER JOIN VERSION v " +
            "ON p.PROJECT_ID = v.PROJECT_ID AND i.VERSION_ID = v.VERSION_ID " +
            "WHERE LOWER(p.PROJECT_NAME) LIKE ifnull(?, LOWER(p.PROJECT_NAME)) " +
            "AND LOWER(i.STATUS) = ifnull(?, LOWER(i.STATUS)) " +
            "AND LOWER(i.OWNER) LIKE ifnull(?, LOWER(i.OWNER)) " +
            "AND LOWER(i.ASSIGNEE) LIKE ifnull(?, LOWER(i.ASSIGNEE)) " +
            "AND LOWER(i.ISSUE_TYPE) = ifnull(?, LOWER(i.ISSUE_TYPE)) " +
            "AND LOWER(i.PRIORITY) = ifnull(?, LOWER(i.PRIORITY)) " +
            "AND LOWER(i.SEVERITY) = ifnull(?, LOWER(i.SEVERITY)) " +
            "AND p.ORGANIZATION_ID = ? " +
            "ORDER BY p.PROJECT_ID, v.VERSION_id, i.ISSUE_ID ASC";

    String SEARCH_ISSUE_BY_SUMMARY_CONTENT = "SELECT p.PROJECT_KEY, p.PROJECT_ID, p.PROJECT_NAME, v.VERSION, i.PKEY, i.SUMMARY, i.ISSUE_TYPE, " +
            "i.PRIORITY, i.OWNER, i.STATUS, i.ASSIGNEE, i.SEVERITY FROM ISSUE i " +
            "INNER JOIN PROJECT p " +
            "ON p.PROJECT_ID = i.PROJECT_ID " +
            "LEFT OUTER JOIN VERSION v " +
            "ON p.PROJECT_ID = v.PROJECT_ID AND i.VERSION_ID = v.VERSION_ID " +
            "WHERE CASE WHEN ? IS NULL THEN TRUE ELSE LOWER(i.SUMMARY) LIKE LOWER(?) END " +
            "AND LOWER(i.STATUS) = ifnull(?, LOWER(i.STATUS)) " +
            "AND LOWER(i.ISSUE_TYPE) = ifnull(?, LOWER(i.ISSUE_TYPE)) " +
            "AND LOWER(i.PRIORITY) = ifnull(?, LOWER(i.PRIORITY)) " +
            "AND LOWER(i.SEVERITY) = ifnull(?, LOWER(i.SEVERITY)) " +
            "AND p.ORGANIZATION_ID = ? " +
            "ORDER BY p.PROJECT_ID, v.VERSION_id, i.ISSUE_ID ASC";

}
