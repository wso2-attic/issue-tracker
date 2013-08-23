package org.wso2.carbon.issue.tracker.dao.impl;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.wso2.carbon.issue.tracker.bean.Comment;
import org.wso2.carbon.issue.tracker.dao.CommentDAO;
import org.wso2.carbon.issue.tracker.util.Constants;
import org.wso2.carbon.issue.tracker.util.DBConfiguration;

/**
 * Implementation of {@link CommentDAO}
 *
 */
public class CommentDAOImpl implements CommentDAO {

    private static final Log log = LogFactory.getLog(CommentDAOImpl.class);

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Comment> getCommentsForIssue(int issueId) throws SQLException {
        Connection dbConnection = null;
        PreparedStatement preparedStatement = null;

        String selectSQL = "SELECT ID, DESCRIPTION, CREATED_TIME, UPDATED_TIME, CREATOR, ISSUE_ID FROM COMMENT WHERE ISSUE_ID = ? ORDER BY ID ASC";
        List<Comment> comments = new ArrayList<Comment>();

        try {
            dbConnection = DBConfiguration.getDBConnection();
            preparedStatement = dbConnection.prepareStatement(selectSQL);
            preparedStatement.setInt(1, issueId);

            // execute select SQL statement
            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()) {

                Comment comment = new Comment();
                comment.setId(rs.getInt("ID"));
                comment.setCommentDescription(rs.getString("DESCRIPTION"));

                Timestamp createdTime = rs.getTimestamp("CREATED_TIME");
                String createdTimeStr = Constants.DATE_FORMAT.format(createdTime);
                comment.setCreatedTime(createdTimeStr);

                Timestamp updatedTime = rs.getTimestamp("UPDATED_TIME");
                String updatedTimeStr = Constants.DATE_FORMAT.format(updatedTime);
                comment.setCreatedTime(updatedTimeStr);

                comment.setCreator(rs.getString("CREATOR"));

                comments.add(comment);
            }

        } catch (SQLException e) {
            String msg = "Error while getting comment from DB, issueID: "+ issueId;
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
        return comments;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean addCommentForIssue(Comment comment, String uniqueKey) throws SQLException {
        Connection dbConnection = null;
        PreparedStatement preparedStatement = null;

        String insertTableSQL = "INSERT INTO COMMENT (DESCRIPTION, CREATED_TIME, UPDATED_TIME, CREATOR, ISSUE_ID) SELECT ?, ?, ?, ?, ISSUE_ID FROM ISSUE WHERE PKEY = ?";

        boolean isInserted = false;
        try {
            dbConnection = DBConfiguration.getDBConnection();
            preparedStatement = dbConnection.prepareStatement(insertTableSQL);

            preparedStatement.setString(1, comment.getCommentDescription());
            preparedStatement.setTimestamp(2, getCurrentTimeStamp());
            preparedStatement.setNull(3, Types.INTEGER);
            preparedStatement.setString(4, comment.getCreator());
            preparedStatement.setString(5, uniqueKey);

            // execute insert SQL statement
            isInserted = preparedStatement.executeUpdate() == 1 ? true : false;

            if(log.isDebugEnabled()){
                log.debug("Record is inserted into COMMENT table!");
            }

        } catch (SQLException e) {
            String msg = "Error while adding comment to DB, commentID: "+ comment.getId();
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
        return isInserted;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean deleteCommentByCommentId(String issuePkey, int commentId) throws SQLException {
        Connection dbConnection = null;
        PreparedStatement preparedStatement = null;

        String deleteSQL = "DELETE c FROM COMMENT c INNER JOIN ISSUE i ON i.ISSUE_ID = c.ISSUE_ID WHERE i.PKEY=? AND c.ID = ?";
        boolean isDeleted = false;
        try {
            dbConnection = DBConfiguration.getDBConnection();
            preparedStatement = dbConnection.prepareStatement(deleteSQL);
            preparedStatement.setString(1, issuePkey);
            preparedStatement.setInt(2, commentId);

            // execute delete SQL statement
            isDeleted = preparedStatement.executeUpdate() == 1 ? true : false;

            if(log.isDebugEnabled()){
                log.debug("Record is deleted from COMMENT table!");
            }

        } catch (SQLException e) {
            String msg = "Error while deleting comment from DB, commentID: " + commentId;
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
        return isDeleted;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean editComment(Comment comment, String uniqueKey) throws SQLException {
        Connection dbConnection = null;
        PreparedStatement preparedStatement = null;

        boolean isUpdated = false;

        String updateTableSQL = "UPDATE COMMENT c INNER JOIN ISSUE i ON c.ISSUE_ID = i.ISSUE_ID SET i.DESCRIPTION = ?, i.UPDATED_TIME = ?  WHERE c.ID=? AND i.PKEY = ? AND c.CREATOR = ? ";

        try {
            dbConnection = DBConfiguration.getDBConnection();
            preparedStatement = dbConnection.prepareStatement(updateTableSQL);

            preparedStatement.setString(1, comment.getCommentDescription());
            preparedStatement.setTimestamp(2, getCurrentTimeStamp());
            preparedStatement.setInt(3, comment.getId());
            preparedStatement.setString(4, uniqueKey);
            preparedStatement.setString(5, comment.getCreator());

            // execute update SQL stetement
            isUpdated = preparedStatement.executeUpdate() == 1 ? true : false;

            if(log.isDebugEnabled()){
                log.debug("Record is updated to COMMENT  table!");
            }

        } catch (SQLException e) {
            String msg = "Error while editing comment to DB, commentID: "+ comment.getId();
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
        return isUpdated;
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
