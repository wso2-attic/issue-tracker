package org.wso2.carbon.issue.tracker.dao.impl;

import org.apache.log4j.Logger;
import org.wso2.carbon.issue.tracker.bean.*;
import org.wso2.carbon.issue.tracker.dao.SearchDAO;
import org.wso2.carbon.issue.tracker.util.Constants;
import org.wso2.carbon.issue.tracker.util.DBConfiguration;

import java.sql.*;
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


        String selectSQL ="SELECT p.PROJECT_KEY, p.PROJECT_ID, p.PROJECT_NAME, v.VERSION, i.PKEY, i.SUMMARY, i.ISSUE_TYPE, i.PRIORITY, i.OWNER, i.STATUS, i.ASSIGNEE, i.SEVERITY FROM ISSUE i " +
                                "INNER JOIN PROJECT p " +
                                "ON p.PROJECT_ID = i.PROJECT_ID " +
                                "LEFT OUTER JOIN VERSION v " +
                                "ON p.PROJECT_ID = v.PROJECT_ID AND i.VERSION_ID = v.VERSION_ID "  +
                                    "WHERE LOWER(p.PROJECT_NAME) LIKE ifnull(?, LOWER(p.PROJECT_NAME)) " +
                                           "AND LOWER(i.STATUS) = ifnull(?, LOWER(i.STATUS)) " +
                                           "AND LOWER(i.OWNER) LIKE ifnull(?, LOWER(i.OWNER)) " +
                                           "AND LOWER(i.ASSIGNEE) LIKE ifnull(?, LOWER(i.ASSIGNEE)) " +
                                           "AND LOWER(i.ISSUE_TYPE) = ifnull(?, LOWER(i.ISSUE_TYPE)) " +
                                           "AND LOWER(i.PRIORITY) = ifnull(?, LOWER(i.PRIORITY)) " +
                                           "AND LOWER(i.SEVERITY) = ifnull(?, LOWER(i.SEVERITY)) " +
                                           "AND p.ORGANIZATION_ID = ? " +
                                    "ORDER BY p.PROJECT_ID, v.VERSION_id, i.ISSUE_ID ASC";

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

            if(projectName!=null && !projectName.equals(""))
                preparedStatement.setString(1, "%"+projectName+"%");
            else
                preparedStatement.setNull(1, Types.VARCHAR);

            preparedStatement.setString(2, searchBean.getIssueStatus());

            if(owner!=null && !owner.equals(""))
                preparedStatement.setString(3, "%"+owner+"%");
            else
                preparedStatement.setNull(3, Types.VARCHAR);

            if(assignee!=null && !assignee.equals(""))
                preparedStatement.setString(4, "%"+assignee+"%");
            else
                preparedStatement.setNull(4, Types.VARCHAR);

            preparedStatement.setString(5, searchBean.getIssueType());
            preparedStatement.setString(6, searchBean.getPriority());
            preparedStatement.setString(7, searchBean.getSeverity());
            preparedStatement.setInt(8, searchBean.getOrganizationId());

            // execute select SQL statement
            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()) {
                SearchResponse response = new SearchResponse();
                response.setProjectKey(rs.getString("PROJECT_KEY"));
                response.setIssuePkey(rs.getString("PKEY"));
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

        String selectSQL = "SELECT p.PROJECT_KEY, p.PROJECT_ID, p.PROJECT_NAME, v.VERSION, i.PKEY, i.SUMMARY, i.ISSUE_TYPE, i.PRIORITY, i.OWNER, i.STATUS, i.ASSIGNEE, i.SEVERITY FROM ISSUE i " +
                            "INNER JOIN PROJECT p " +
                            "ON p.PROJECT_ID = i.PROJECT_ID " +
                            "LEFT OUTER JOIN VERSION v " +
                            "ON p.PROJECT_ID = v.PROJECT_ID AND i.VERSION_ID = v.VERSION_ID " +
                            "WHERE LOWER(i.SUMMARY) LIKE LOWER(?) " +
                                "AND LOWER(i.STATUS) = ifnull(?, LOWER(i.STATUS)) " +
                                "AND LOWER(i.ISSUE_TYPE) = ifnull(?, LOWER(i.ISSUE_TYPE)) " +
                                "AND LOWER(i.PRIORITY) = ifnull(?, LOWER(i.PRIORITY)) " +
                                "AND LOWER(i.SEVERITY) = ifnull(?, LOWER(i.SEVERITY)) " +
                                "AND p.ORGANIZATION_ID = ? " +
                                "ORDER BY p.PROJECT_ID, v.VERSION_id, i.ISSUE_ID ASC";


        try {
            dbConnection = DBConfiguration.getDBConnection();
            preparedStatement = dbConnection.prepareStatement(selectSQL);

            String searchValue = searchBean.getSearchValue();
            String status = searchBean.getIssueStatus();

            preparedStatement.setString(1, "%"+searchValue+"%");
            preparedStatement.setString(2, searchBean.getIssueStatus());
            preparedStatement.setString(3, searchBean.getIssueType());
            preparedStatement.setString(4, searchBean.getPriority());
            preparedStatement.setString(5, searchBean.getSeverity());
            preparedStatement.setInt(6, searchBean.getOrganizationId());

            // execute select SQL statement
            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()) {
                SearchResponse response = new SearchResponse();
                response.setIssuePkey(rs.getString("PKEY"));
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
                response.setProjectKey(rs.getString("PROJECT_KEY"));
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
