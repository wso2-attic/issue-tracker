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

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.wso2.carbon.issue.tracker.bean.Comment;
import org.wso2.carbon.issue.tracker.bean.Issue;
import org.wso2.carbon.issue.tracker.util.DBConfiguration;

public class IssueDAO {
	Log log = LogFactory.getLog(IssueDAO.class);
	Connection dbConnection = null;

	public IssueDAO() {
		dbConnection = DBConfiguration.getDBConnection();
		log.debug("database connection successfully retrieved");
	}

	public void createIssue(Issue issue) {

		try {
			PreparedStatement st = (PreparedStatement) dbConnection
					.prepareStatement("INSERT INTO Issues VALUES(?,?,?,?,?,?,?,?,?,?,?,?)");
			st.setString(1, Integer.toString(issue.getId()));
			st.setString(2, issue.getSummary());
			st.setString(3, issue.getDescription());
			st.setString(4, issue.getType());
			st.setString(5, issue.getPriority());
			st.setString(6, issue.getOwner());
			st.setString(7, issue.getStatus());
			st.setString(8, issue.getAssignee());
			st.setString(9, issue.getVersion());
			st.setString(10, issue.getCreatedTime());
			st.setString(11, issue.getUpdatedTime());
			st.setString(12, issue.getSeverity());
			st.executeUpdate();
		} catch (SQLException e) {
			log.info("Erro occure while creating the issue " + issue.getId()
					+ " " + e.getMessage());
		}

	}

	/**
	 * Any column of the issue can be updated using this method. REMEBER!! This
	 * is to use only one attribute of the issue is changed
	 * 
	 * @param issue
	 *            issue to be changed
	 * @param columnName
	 *            column name
	 * @param value
	 *            new value
	 */
	public void updateIssueAttribute(Issue issue, String columnName,
			String value) {
		try {
			PreparedStatement st = (PreparedStatement) dbConnection
					.prepareStatement("UPDATE Issues SET ?=? WHERE id=?");
			st.setString(1, columnName);
			st.setString(2, value);
			st.setString(3, Integer.toString(issue.getId()));
			st.executeUpdate();
		} catch (SQLException e) {
			log.info("Erro occure while creating the issue " + issue.getId()
					+ " " + e.getMessage());
		}
	}

	public void updateWithComment(Issue issue, Comment comment) {
		

	}

	public void viewIssue(Issue issue) {

	}

	public void viewAllIssues() {

	}
}