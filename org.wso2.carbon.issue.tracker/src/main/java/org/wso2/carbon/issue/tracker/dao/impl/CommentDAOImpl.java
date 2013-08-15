package org.wso2.carbon.issue.tracker.dao.impl;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.wso2.carbon.issue.tracker.bean.Comment;
import org.wso2.carbon.issue.tracker.dao.CommentDAO;
import org.wso2.carbon.issue.tracker.util.DBConfiguration;

import java.sql.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Implementation of {@link CommentDAO}
 *
 */
public class CommentDAOImpl implements CommentDAO {
    private static final DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
    private static final Log log = LogFactory.getLog(CommentDAOImpl.class);

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Comment> getCommentsForIssue(int issueId) throws SQLException {
        Connection dbConnection = null;
        PreparedStatement preparedStatement = null;

        String selectSQL = "SELECT ID, COMMENT, CREATED_TIME, UPDATED_TIME, CREATOR, ISSUE_ID FROM COMMENT WHERE ISSUE_ID = ? ORDER BY ID ASC";
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
                comment.setComment(rs.getString("COMMENT"));

                Timestamp createdTime = rs.getTimestamp("CREATED_TIME");
                String createdTimeStr = dateFormat.format(createdTime);
                comment.setCreatedTime(createdTimeStr);

                Timestamp updatedTime = rs.getTimestamp("UPDATED_TIME");
                String updatedTimeStr = dateFormat.format(updatedTime);
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
    public void addCommentForIssue(Comment comment, int issueId) throws SQLException {
        Connection dbConnection = null;
        PreparedStatement preparedStatement = null;

        String insertTableSQL = "INSERT INTO COMMENT (COMMENT, CREATED_TIME, UPDATED_TIME, CREATOR, ISSUE_ID) VALUES (?, ?, ?, ?, ?)";

        try {
            dbConnection = DBConfiguration.getDBConnection();
            preparedStatement = dbConnection.prepareStatement(insertTableSQL);

            preparedStatement.setString(1, comment.getComment());
            preparedStatement.setTimestamp(2, getCurrentTimeStamp());
            preparedStatement.setTimestamp(3, getCurrentTimeStamp());
            preparedStatement.setString(4, comment.getCreator());
            preparedStatement.setInt(5, issueId);

            // execute insert SQL statement
            preparedStatement.executeUpdate();

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
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean deleteCommentByCommentId(int issueId, int commentId) throws SQLException {
        Connection dbConnection = null;
        PreparedStatement preparedStatement = null;

        String deleteSQL = "DELETE FROM COMMENT WHERE ISSUE_ID= ? AND ID = ?";
        boolean result = false;
        try {
            dbConnection = DBConfiguration.getDBConnection();
            preparedStatement = dbConnection.prepareStatement(deleteSQL);
            preparedStatement.setInt(1, issueId);
            preparedStatement.setInt(2, commentId);
            // execute delete SQL statement
            int count = preparedStatement.executeUpdate();

            if(count==0)
                result = false;
            else
                result = true;

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
        return result;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void editComment(Comment comment, int issueId) throws SQLException {
        Connection dbConnection = null;
        PreparedStatement preparedStatement = null;

        String updateTableSQL = "UPDATE COMMENT SET COMMENT = ?, UPDATED_TIME = ? WHERE ISSUE_ID=? AND ID = ?";


        try {
            dbConnection = DBConfiguration.getDBConnection();
            preparedStatement = dbConnection.prepareStatement(updateTableSQL);

            preparedStatement.setString(1, comment.getComment());
            preparedStatement.setTimestamp(2, getCurrentTimeStamp());
            preparedStatement.setInt(3, issueId);
            preparedStatement.setInt(4, comment.getId());

            // execute update SQL stetement
            preparedStatement.executeUpdate();

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
