package org.wso2.carbon.issue.tracker.dao.impl;

import org.apache.cxf.common.util.StringUtils;
import org.apache.log4j.Logger;
import org.wso2.carbon.issue.tracker.bean.*;
import org.wso2.carbon.issue.tracker.dao.SearchDAO;
import org.wso2.carbon.issue.tracker.util.Constants;
import org.wso2.carbon.issue.tracker.util.DBConfiguration;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Implementation of {@link SearchDAO}
 */
public class SearchDAOImpl implements SearchDAO{
    private static Logger logger = Logger.getLogger(SearchDAOImpl.class);

    /**
     * {@inheritDoc}
     */
    @Override
    public List <SearchResponse> searchIssue(SearchBean searchBean) throws SQLException {
        Connection dbConnection = null;
        PreparedStatement preparedStatement = null;
        List <SearchResponse> resultList = new ArrayList<SearchResponse>();

        String selectSQL = "SELECT p.PROJECT_ID, p.PROJECT_NAME, v.VERSION, i.PKEY, i.SUMMARY, i.ISSUE_TYPE, i.PRIORITY, i.OWNER, i.STATUS, i.ASSIGNEE, i.SEVERITY FROM PROJECT p " +
                           "LEFT OUTER JOIN VERSION v "+
                                "ON p.PROJECT_ID = v.PROJECT_ID " +
                                "INNER JOIN ISSUE i " +
                                    "ON p.PROJECT_ID = i.PROJECT_ID " +
                                    "WHERE LOWER(p.PROJECT_NAME) = ifnull(?, LOWER(p.PROJECT_NAME)) " +
                                           "AND LOWER(i.STATUS) = ifnull(?, LOWER(i.STATUS)) " +
                                           "AND LOWER(i.OWNER) = ifnull(?, LOWER(i.OWNER)) " +
                                           "AND LOWER(i.ASSIGNEE) = ifnull(?, LOWER(i.ASSIGNEE)) " +
                                           "AND LOWER(i.ISSUE_TYPE) = ifnull(?, LOWER(i.ISSUE_TYPE)) " +
                                           "AND LOWER(i.PRIORITY) = ifnull(?, LOWER(i.PRIORITY)) " +
                                           "AND LOWER(i.SEVERITY) = ifnull(?, LOWER(i.SEVERITY)) " +
                                    "ORDER BY p.PROJECT_ID, v.VERSION_id, i.ISSUE_ID ASC";


        System.out.println("SQL: " + selectSQL);
        try {
            dbConnection = DBConfiguration.getDBConnection();
            preparedStatement = dbConnection.prepareStatement(selectSQL);

            int searchType = searchBean.getSearchType();
            String searchValue = searchBean.getSearchValue();

            String projectName = null;
            String owner = null;
            String assignee = null;

            if(searchType == Constants.BY_PROJECT_NAME)
                projectName = searchValue;
            else if(searchType == Constants.BY_ASSIGNEE)
                assignee = searchValue;
            else if(searchType == Constants.BY_OWNER)
                owner = searchValue;

            preparedStatement.setString(1, projectName);
            preparedStatement.setString(2, searchBean.getIssueStatus());
            preparedStatement.setString(3, owner);
            preparedStatement.setString(4, assignee);
            preparedStatement.setString(5, searchBean.getIssueType());
            preparedStatement.setString(6, searchBean.getPriority());
            preparedStatement.setString(7, searchBean.getSeverity());

            // execute select SQL statement
            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()) {
                SearchResponse response = new SearchResponse();
                response.setPkey(rs.getString("PKEY"));
                response.setSummary(rs.getString("SUMMARY"));
                response.setIssueType(rs.getString("ISSUE_TYPE"));
                response.setPriority(rs.getString("PRIORITY"));
                response.setOwner(rs.getString("OWNER"));
                response.setStatus(rs.getString("STATUS"));
                response.setAssignee(rs.getString("ASSIGNEE"));
                response.setSeverity(rs.getString("SEVERITY"));

                response.setVersion(rs.getString("VERSION"));
                response.setProjectId(rs.getInt("PROJECT_ID"));
                response.setProjectName(rs.getString("PROJECT_NAME"));
                resultList.add(response);

            }

        } catch (SQLException e) {
            String msg = "Error while getting comment from DB, issueID: ";
            logger.error(msg, e);
            throw e;
        } finally {

            if (preparedStatement != null) {
                preparedStatement.close();
            }

            if (dbConnection != null) {
                dbConnection.close();
            }

        }
        return resultList;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List <SearchResponse> searchIssueBySummaryContent(SearchBean searchBean) throws SQLException {
        Connection dbConnection = null;
        PreparedStatement preparedStatement = null;
        List <SearchResponse> resultList = new ArrayList<SearchResponse>();

        String selectSQL = "SELECT p.PROJECT_ID, p.PROJECT_NAME, v.VERSION, i.PKEY, i.SUMMARY, i.ISSUE_TYPE, i.PRIORITY, i.OWNER, i.STATUS, i.ASSIGNEE, i.SEVERITY FROM PROJECT p " +
                            "LEFT OUTER JOIN VERSION v "+
                            "ON p.PROJECT_ID = v.PROJECT_ID " +
                            "INNER JOIN ISSUE i " +
                            "ON p.PROJECT_ID = i.PROJECT_ID " +
                            "WHERE i.STATUS = ? "+
                                "AND LOWER(i.SUMMARY) LIKE LOWER(?) " +

                                "AND LOWER(i.STATUS) = ifnull(?, LOWER(i.STATUS)) " +
                                "AND LOWER(i.ISSUE_TYPE) = ifnull(?, LOWER(i.ISSUE_TYPE)) " +
                                "AND LOWER(i.PRIORITY) = ifnull(?, LOWER(i.PRIORITY)) " +
                                "AND LOWER(i.SEVERITY) = ifnull(?, LOWER(i.SEVERITY)) " +

                                "ORDER BY p.PROJECT_ID, v.VERSION_id, i.ISSUE_ID ASC";

        System.out.println("SQL: " + selectSQL);
        try {
            dbConnection = DBConfiguration.getDBConnection();
            preparedStatement = dbConnection.prepareStatement(selectSQL);

            String searchValue = searchBean.getSearchValue();
            String status = searchBean.getIssueStatus();

            preparedStatement.setString(1, status);
            preparedStatement.setString(2, "%"+searchValue+"%");
            preparedStatement.setString(3, searchBean.getIssueStatus());
            preparedStatement.setString(4, searchBean.getIssueType());
            preparedStatement.setString(5, searchBean.getPriority());
            preparedStatement.setString(6, searchBean.getSeverity());

            // execute select SQL statement
            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()) {
                SearchResponse response = new SearchResponse();
                response.setPkey(rs.getString("PKEY"));
                response.setSummary(rs.getString("SUMMARY"));
                response.setIssueType(rs.getString("ISSUE_TYPE"));
                response.setPriority(rs.getString("PRIORITY"));
                response.setOwner(rs.getString("OWNER"));
                response.setStatus(rs.getString("STATUS"));
                response.setAssignee(rs.getString("ASSIGNEE"));
                response.setSeverity(rs.getString("SEVERITY"));

                response.setVersion(rs.getString("VERSION"));
                response.setProjectId(rs.getInt("PROJECT_ID"));
                response.setProjectName(rs.getString("PROJECT_NAME"));
                resultList.add(response);

            }

        } catch (SQLException e) {
            String msg = "Error while getting comment from DB, issueID: ";
            logger.error(msg, e);
            throw e;
        } finally {

            if (preparedStatement != null) {
                preparedStatement.close();
            }

            if (dbConnection != null) {
                dbConnection.close();
            }

        }
        return resultList;
    }


}
