/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements. See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership. The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License. You may obtain a copy of the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package org.wso2.carbon.issue.tracker.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

import org.apache.log4j.Logger;
import org.wso2.carbon.issue.tracker.bean.Comment;
import org.wso2.carbon.issue.tracker.bean.Issue;
import org.wso2.carbon.issue.tracker.dao.IssueDAO;
import org.wso2.carbon.issue.tracker.util.Constants;
import org.wso2.carbon.issue.tracker.util.DBConfiguration;

public class IssueDAOImpl implements IssueDAO {
	Logger logger = Logger.getLogger(IssueDAOImpl.class);

	public boolean add(Issue issue) throws SQLException {
		int result = 0;
		PreparedStatement st = null;
		Connection dbConnection = null;
		try {

			dbConnection = DBConfiguration.getDBConnection();

			st = (PreparedStatement) dbConnection
					.prepareStatement("INSERT INTO ISSUE(ISSUE_ID,PKEY,PROJECT_ID,SUMMARY,DESCRIPTION,"
							+ "ISSUE_TYPE,PRIORITY,OWNER,STATUS,ASSIGNEE,VERSION_ID,"
							+ "CREATED_TIME,UPDATED_TIME,SEVERITY)"
							+ " VALUES(?,?,?,?,?,?,?,?,?,?,?,?)");
			st.setString(1, Integer.toString(issue.getId()));
			st.setString(2, issue.getSummary());
			st.setString(3, issue.getDescription());
			st.setString(4, issue.getType());
			st.setString(5, issue.getPriority());
			st.setString(6, issue.getOwner());
			st.setString(7, issue.getStatus());
			st.setString(8, issue.getAssignee());
			st.setInt(9, issue.getVersion());
			st.setString(10, issue.getCreatedTime());
			st.setString(11, issue.getUpdatedTime());
			st.setString(12, issue.getSeverity());
			result = st.executeUpdate();
		} catch (SQLException e) {
			logger.info("Erro occure while creating the issue " + issue.getId()
					+ " " + e.getMessage());
			throw e;
		} finally {
			closeStatement(st, dbConnection);
		}
		return (result > 0);

	}

	private void closeStatement(PreparedStatement st, Connection dbConnection) {
		try {
			if (st != null) {
				st.close();
			}

			if (dbConnection != null) {
				dbConnection.close();
			}
		} catch (SQLException ignore) {

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
	public boolean updateAttribute(Issue issue, String columnName, String value)
			throws SQLException {
		int result=0;
		Connection dbConnection = null;
		PreparedStatement st = null;
		try {
			dbConnection = DBConfiguration.getDBConnection();
			st = (PreparedStatement) dbConnection
					.prepareStatement("UPDATE ISSUE SET ?=? WHERE id=?");

			st.setString(1, columnName);
			st.setString(2, value);
			st.setString(3, Integer.toString(issue.getId()));
			result=st.executeUpdate();
		} catch (SQLException e) {
			logger.debug("Erro occure while creating the issue "
					+ issue.getId() + " " + e.getMessage());
			throw e;
		} finally {
			closeStatement(st, dbConnection);
		}
		return (result>0);
	}

	public void addComment(Issue issue, Comment comment) throws SQLException {

	}

	public Issue getIssueByKey(String uniqueKey) throws SQLException {

		PreparedStatement st = null;
		Connection dbConnection = null;
		Issue issue = null;
		try {
			dbConnection = DBConfiguration.getDBConnection();

			st = (PreparedStatement) dbConnection
					.prepareStatement("SELECT ISSUE_ID,PKEY,PROJECT_ID,SUMMARY,DESCRIPTION,ISSUE_TYPE,PRIORITY,OWNER,STATUS,ASSIGNEE,VERSION_ID,CREATED_TIME,UPDATED_TIME,SEVERITY FROM ISSUE WHERE PKEY = ?");
			st.setMaxRows(1);
			st.setString(1, uniqueKey);

			ResultSet rs = st.executeQuery();
			if (rs.first()) {

				issue = new Issue();
				issue.setId(rs.getInt("ISSUE_ID"));
				issue.setKey(rs.getString("PKEY"));
				issue.setProjectId(rs.getInt("PROJECT_ID"));
				issue.setSummary(rs.getString("SUMMARY"));
				issue.setDescription(rs.getString("DESCRIPTION"));
				issue.setType(rs.getString("ISSUE_TYPE"));
				issue.setPriority(rs.getString("PRIORITY"));
				issue.setOwner(rs.getString("OWNER"));
				issue.setStatus(rs.getString("STATUS"));
				issue.setAssignee(rs.getString("ASSIGNEE"));
				issue.setVersion(rs.getInt("VERSION_ID"));
				issue.setSeverity(rs.getString("SEVERITY"));

				Timestamp createdTime = rs.getTimestamp("CREATED_TIME");
				String createdTimeStr = Constants.DATE_FORMAT
						.format(createdTime);
				issue.setCreatedTime(createdTimeStr);

				Timestamp updatedTime = rs.getTimestamp("UPDATED_TIME");
				String updatedTimeStr = Constants.DATE_FORMAT
						.format(updatedTime);
				issue.setUpdatedTime(updatedTimeStr);

			}

		} catch (SQLException e) {
			logger.error(e.getMessage(), e);
			throw e;

		} finally {
			closeStatement(st, dbConnection);

		}
		return issue;
	}

	public Issue getIssueById(int id) throws SQLException {

		PreparedStatement st = null;
		Connection dbConnection = null;
		Issue issue = null;
		try {
			dbConnection = DBConfiguration.getDBConnection();

			st = (PreparedStatement) dbConnection
					.prepareStatement("SELECT ISSUE_ID,PKEY,PROJECT_ID,SUMMARY,DESCRIPTION,ISSUE_TYPE,PRIORITY,OWNER,STATUS,ASSIGNEE,VERSION_ID,CREATED_TIME,UPDATED_TIME,SEVERITY FROM ISSUE WHERE ISSUE_ID = ?");
			st.setMaxRows(1);
			st.setInt(1, id);

			ResultSet rs = st.executeQuery();
			if (rs.first()) {

				issue = new Issue();
				issue.setId(rs.getInt("ISSUE_ID"));
				issue.setKey(rs.getString("PKEY"));
				issue.setProjectId(rs.getInt("PROJECT_ID"));
				issue.setSummary(rs.getString("SUMMARY"));
				issue.setDescription(rs.getString("DESCRIPTION"));
				issue.setType(rs.getString("ISSUE_TYPE"));
				issue.setPriority(rs.getString("PRIORITY"));
				issue.setOwner(rs.getString("OWNER"));
				issue.setStatus(rs.getString("STATUS"));
				issue.setAssignee(rs.getString("ASSIGNEE"));
				issue.setVersion(rs.getInt("VERSION_ID"));
				issue.setSeverity(rs.getString("SEVERITY"));

				Timestamp createdTime = rs.getTimestamp("CREATED_TIME");
				String createdTimeStr = Constants.DATE_FORMAT
						.format(createdTime);
				issue.setCreatedTime(createdTimeStr);

				Timestamp updatedTime = rs.getTimestamp("UPDATED_TIME");
				String updatedTimeStr = Constants.DATE_FORMAT
						.format(updatedTime);
				issue.setUpdatedTime(updatedTimeStr);

			}

		} catch (SQLException e) {
			logger.error(e.getMessage(), e);
			throw e;

		} finally {
			closeStatement(st, dbConnection);

		}
		return issue;
	}

}