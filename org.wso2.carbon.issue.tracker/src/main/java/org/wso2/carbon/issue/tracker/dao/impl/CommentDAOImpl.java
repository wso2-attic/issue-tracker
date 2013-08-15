package org.wso2.carbon.issue.tracker.dao.impl;

import org.wso2.carbon.issue.tracker.bean.Comment;
import org.wso2.carbon.issue.tracker.dao.CommentDAO;
import org.wso2.carbon.issue.tracker.util.DBConfiguration;

import java.sql.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class CommentDAOImpl implements CommentDAO {
    private static final DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");


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

            // execute select SQL stetement
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

    public boolean postCommentForIssue(Comment comment) throws SQLException {
        Connection dbConnection = null;
        PreparedStatement preparedStatement = null;

        String insertTableSQL = "INSERT INTO COMMENT (COMMENT, CREATED_TIME, UPDATED_TIME, CREATOR, ISSUE_ID) VALUES (?, ?, ?, ?, ?)";

        try {
            System.out.println("Comment: " + comment.getComment());
            System.out.println("issue_id: " + comment.getIssueId());


            dbConnection = DBConfiguration.getDBConnection();
            preparedStatement = dbConnection.prepareStatement(insertTableSQL);

            preparedStatement.setString(1, comment.getComment());
            preparedStatement.setTimestamp(2, getCurrentTimeStamp());
            preparedStatement.setTimestamp(3, getCurrentTimeStamp());
            preparedStatement.setString(4, comment.getCreator());
            preparedStatement.setInt(5, comment.getIssueId());

            // execute insert SQL stetement
            preparedStatement.executeUpdate();

            System.out.println("Record is inserted into COMMENT table!");

        } catch (SQLException e) {

            System.out.println(e.getMessage());
            return false;
        } finally {

            if (preparedStatement != null) {
                preparedStatement.close();
            }

            if (dbConnection != null) {
                dbConnection.close();
            }

        }


        return true;
    }

    @Override
    public boolean deleteCommentByCommentId(int commentId, String creator) throws SQLException {
        Connection dbConnection = null;
        PreparedStatement preparedStatement = null;

        String deleteSQL = "DELETE COMMENT WHERE ID = ? AND USER_ID = ?";

        try {
            dbConnection = DBConfiguration.getDBConnection();
            preparedStatement = dbConnection.prepareStatement(deleteSQL);
            preparedStatement.setInt(1, commentId);
            preparedStatement.setString(2, creator);

            // execute delete SQL statement
            int x = preparedStatement.executeUpdate();


            System.out.println("Record is deleted! " + x);

        } catch (SQLException e) {

            System.out.println(e.getMessage());
            return false;
        } finally {

            if (preparedStatement != null) {
                preparedStatement.close();
            }

            if (dbConnection != null) {
                dbConnection.close();
            }

        }
        return true;
    }

    @Override
    public boolean editComment(Comment comment) throws SQLException {
        Connection dbConnection = null;
        PreparedStatement preparedStatement = null;

        String updateTableSQL = "UPDATE COMMENT SET COMMENT = ?, UPDATED_TIME = ? WHERE ID? AND CREATOR=?";


        try {
            dbConnection = DBConfiguration.getDBConnection();
            preparedStatement = dbConnection.prepareStatement(updateTableSQL);

            preparedStatement.setString(1, comment.getComment());
            preparedStatement.setTimestamp(2, getCurrentTimeStamp());
            preparedStatement.setInt(3, comment.getId());
            preparedStatement.setInt(4, comment.getIssueId());
            preparedStatement.setString(5, comment.getCreator());

            // execute update SQL stetement
            preparedStatement.executeUpdate();

            System.out.println("Record is updated to COMMENT  table!");

        } catch (SQLException e) {

            System.out.println(e.getMessage());
            return false;
        } finally {

            if (preparedStatement != null) {
                preparedStatement.close();
            }

            if (dbConnection != null) {
                dbConnection.close();
            }

        }



        return true;
    }

    @Override
    public boolean isOwnerOfComment(int commentId, String creator) throws SQLException {
        Connection dbConnection = null;
        PreparedStatement preparedStatement = null;

        String selectSQL = "SELECT COUNT(ID) FROM COMMENT WHERE ID =? AND CREATOR = ?";
        int count = -1;


        try {
            dbConnection = DBConfiguration.getDBConnection();
            preparedStatement = dbConnection.prepareStatement(selectSQL);
            preparedStatement.setInt(1, commentId);
            preparedStatement.setString(2, creator);

            // execute select SQL stetement
            ResultSet rs = preparedStatement.executeQuery();
           if(rs.next())
               count = rs.getInt(1);

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
        return count==0?true:false;
    }

    private static Timestamp getCurrentTimeStamp() {
        java.util.Date today = new java.util.Date();
        return new Timestamp(today.getTime());
    }

}
