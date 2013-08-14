package org.wso2.carbon.issue.tracker.dao.impl;

import org.wso2.carbon.issue.tracker.bean.Comment;
import org.wso2.carbon.issue.tracker.dao.CommentDAO;
import org.wso2.carbon.issue.tracker.util.DBConfiguration;

import java.sql.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: nihanth
 * Date: 8/14/13
 * Time: 9:35 AM
 * To change this template use File | Settings | File Templates.
 */
public class CommentDAOImpl implements CommentDAO {
    private static final DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");


   @Override
    public List<Comment> getCommentsForIssue(int issueId) throws SQLException {
        Connection dbConnection = null;
        PreparedStatement preparedStatement = null;

        String selectSQL = "SELECT COMMENT_ID, COMMENT, CREATED_TIME, UPDATED_TIME, COMMENT_CREATOR, ISSUE_ID FROM COMMENT WHERE ISSUE_ID = ? ORDER BY COMMENT_ID ASC";
        List<Comment> comments = new ArrayList<Comment>();

        try {
            dbConnection = DBConfiguration.getDBConnection();
            preparedStatement = dbConnection.prepareStatement(selectSQL);
            preparedStatement.setInt(1, issueId);

            // execute select SQL stetement
            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()) {

                Comment comment = new Comment();
                comment.setCommentId(rs.getInt("COMMENT_ID"));
                comment.setComment(rs.getString("COMMENT"));

                Timestamp createdTime = rs.getTimestamp("CREATED_TIME");
                String createdTimeStr = dateFormat.format(createdTime);
                comment.setCreatedTime(createdTimeStr);

                Timestamp updatedTime = rs.getTimestamp("UPDATED_TIME");
                String updatedTimeStr = dateFormat.format(updatedTime);
                comment.setCreatedTime(updatedTimeStr);

                comment.setCommentCreator(rs.getString("COMMENT_CREATOR"));

                comment.setIssueId(rs.getInt("ISSUE_ID"));

                comments.add(comment);

            }

        } catch (SQLException e) {

            System.out.println(e.getMessage());

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

    public String postCommentForIssue(Comment comment) throws SQLException {
        Connection dbConnection = null;
        PreparedStatement preparedStatement = null;

        String insertTableSQL = "INSERT INTO COMMENT (COMMENT, CREATED_TIME, UPDATED_TIME, COMMENT_CREATOR, ISSUE_ID) VALUES (?, ?, ?, ?, ?)";

        try {
            System.out.println("Comment: " + comment.getComment());
            System.out.println("issue_id: " + comment.getIssueId());


            dbConnection = DBConfiguration.getDBConnection();
            preparedStatement = dbConnection.prepareStatement(insertTableSQL);

            preparedStatement.setString(1, comment.getComment());
            preparedStatement.setTimestamp(2, getCurrentTimeStamp());
            preparedStatement.setTimestamp(3, getCurrentTimeStamp());
            preparedStatement.setString(4, comment.getCommentCreator());
            preparedStatement.setInt(5, comment.getIssueId());

            // execute insert SQL stetement
            preparedStatement.executeUpdate();

            System.out.println("Record is inserted into COMMENT table!");

        } catch (SQLException e) {

            System.out.println(e.getMessage());

        } finally {

            if (preparedStatement != null) {
                preparedStatement.close();
            }

            if (dbConnection != null) {
                dbConnection.close();
            }

        }


        return null;
    }

    @Override
    public String deleteCommentByCommentId(int userId, int commentId) throws SQLException {
        Connection dbConnection = null;
        PreparedStatement preparedStatement = null;

        String deleteSQL = "DELETE COMMENT WHERE COMMENT_ID = ? AND USER_ID = ?";

        try {
            dbConnection = DBConfiguration.getDBConnection();
            preparedStatement = dbConnection.prepareStatement(deleteSQL);
            preparedStatement.setInt(1, commentId);
            preparedStatement.setInt(2, userId);

            // execute delete SQL statement
            preparedStatement.executeUpdate();

            System.out.println("Record is deleted!");

        } catch (SQLException e) {

            System.out.println(e.getMessage());

        } finally {

            if (preparedStatement != null) {
                preparedStatement.close();
            }

            if (dbConnection != null) {
                dbConnection.close();
            }

        }
        return null;
    }

    @Override
    public String editComment(Comment comment) throws SQLException {
        Connection dbConnection = null;
        PreparedStatement preparedStatement = null;

        String updateTableSQL = "UPDATE COMMENT SET COMMENT = ?, UPDATED_TIME = ? WHERE COMMENT_ID? AND ISSUE_ID=?";


        try {
            dbConnection = DBConfiguration.getDBConnection();
            preparedStatement = dbConnection.prepareStatement(updateTableSQL);

            preparedStatement.setString(1, comment.getComment());
            preparedStatement.setTimestamp(2, getCurrentTimeStamp());
            preparedStatement.setInt(3, comment.getCommentId());
            preparedStatement.setInt(4, comment.getIssueId());

            // execute update SQL stetement
            preparedStatement.executeUpdate();

            System.out.println("Record is updated to COMMENT  table!");

        } catch (SQLException e) {

            System.out.println(e.getMessage());

        } finally {

            if (preparedStatement != null) {
                preparedStatement.close();
            }

            if (dbConnection != null) {
                dbConnection.close();
            }

        }



        return null;
    }

    private static Timestamp getCurrentTimeStamp() {
        java.util.Date today = new java.util.Date();
        return new Timestamp(today.getTime());
    }

}
