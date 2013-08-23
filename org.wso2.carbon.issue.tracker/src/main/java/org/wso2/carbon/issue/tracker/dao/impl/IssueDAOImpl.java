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

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

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
							+ " VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
			st.setInt(1, issue.getId());
            st.setString(2, issue.getKey());
            st.setInt(3, issue.getProjectId());
			st.setString(4, issue.getSummary());
			st.setString(5, issue.getDescription());
			st.setString(6, issue.getType());
			st.setString(7, issue.getPriority());
			st.setString(8, issue.getOwner());
			st.setString(9, issue.getStatus());
			st.setString(10, issue.getAssignee());
            if(issue.getVersion()> 0)
			    st.setInt(11, issue.getVersion());
            else
                st.setNull(11, Types.INTEGER);

			st.setTimestamp(12, getCurrentTimeStamp());
			st.setTimestamp(13, getCurrentTimeStamp());
			st.setString(14, issue.getSeverity());
			result = st.executeUpdate();
		} catch (SQLException e) {
			logger.info("Error occurred while creating the issue " + issue.getId()
					+ " " + e.getMessage());
			throw e;
		} finally {
			closeStatement(st, dbConnection);
		}
		return (result > 0);

	}

    @Override
    public boolean update(Issue issue) throws SQLException {
        boolean result = false;
        PreparedStatement st = null;
        Connection dbConnection = null;
        try {

            dbConnection = DBConfiguration.getDBConnection();

            st = (PreparedStatement) dbConnection
                    .prepareStatement("UPDATE ISSUE SET DESCRIPTION = ?, "
                            + "ISSUE_TYPE = ?, PRIORITY = ?, STATUS = ?, ASSIGNEE = ?, VERSION_ID = ?, "
                            + "UPDATED_TIME = ?, SEVERITY = ? WHERE PKEY = ? AND OWNER = ?");

            st.setString(1, issue.getDescription());
            st.setString(2, issue.getType());
            st.setString(3, issue.getPriority());
            st.setString(4, issue.getStatus());
            st.setString(5, issue.getAssignee());
            if(issue.getVersion() > 0)
                st.setInt(6, issue.getVersion());
            else
                st.setNull(6, Types.INTEGER);

            st.setTimestamp(7, getCurrentTimeStamp());
            st.setString(8, issue.getSeverity());
            st.setString(9, issue.getKey());
            st.setString(10, issue.getOwner());

            result = st.executeUpdate() == 1 ? true : false;
        } catch (SQLException e) {
            logger.info("Error occurred while updating the issue " + issue.getId()
                    + " " + e.getMessage());
            throw e;
        } finally {
            closeStatement(st, dbConnection);
        }
        return result;
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

    @Override
    public List<Issue> getAllIssuesOfProject(int projectId) throws SQLException {
        PreparedStatement st = null;
        Connection dbConnection = null;
        List<Issue> issueList = new ArrayList<Issue>();

        try {
            dbConnection = DBConfiguration.getDBConnection();

            st = (PreparedStatement) dbConnection
                    .prepareStatement("SELECT ISSUE_ID,PKEY,PROJECT_ID,SUMMARY,DESCRIPTION,ISSUE_TYPE,PRIORITY,OWNER,STATUS,ASSIGNEE,VERSION_ID,CREATED_TIME,UPDATED_TIME,SEVERITY FROM ISSUE WHERE PROJECT_ID = ?");
            st.setInt(1, projectId);

            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                Issue issue = new Issue();
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

                issueList.add(issue);
            }

        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
            throw e;

        } finally {
            closeStatement(st, dbConnection);

        }
        return issueList;
    }

    /**
     * Get current time to log DB
     * @return   {@link Timestamp}
     */
    private static Timestamp getCurrentTimeStamp() {
        java.util.Date today = new java.util.Date();
        return new Timestamp(today.getTime());
    }

}