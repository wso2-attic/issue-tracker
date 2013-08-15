package org.wso2.carbon.issue.tracker.server.impl;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.wso2.carbon.issue.tracker.bean.Comment;
import org.wso2.carbon.issue.tracker.dao.CommentDAO;
import org.wso2.carbon.issue.tracker.delegate.DAODeligate;
import org.wso2.carbon.issue.tracker.server.CommentService;

import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.sql.SQLException;
import java.util.List;

public class CommentServiceImpl implements CommentService {

    private static final Log log = LogFactory.getLog(CommentServiceImpl.class);

    public Response getCommentsForIssue(int issueId)  {
        if(log.isDebugEnabled()){
            log.debug("Executing getCommentsForIssue, issueId: " + issueId);
        }

        CommentDAO commentDAO = DAODeligate.getCommentInstance();
        List<Comment> comments = null;
        try {
            comments = commentDAO.getCommentsForIssue(issueId);
        } catch (SQLException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).type(MediaType.APPLICATION_JSON_TYPE).build();
        }

        GenericEntity<List<Comment>> entity = new GenericEntity<List<Comment>>(comments){} ;
        return Response.ok().entity(entity).type(MediaType.APPLICATION_JSON_TYPE).build();
    }

    public Response postCommentForIssue(Comment comment) {
        if(log.isDebugEnabled()){
            log.debug("Executing postCommentForIssue, created by: " + comment.getCreator());
        }

        CommentDAO commentDAO = DAODeligate.getCommentInstance();
        try {
            commentDAO.postCommentForIssue(comment);
            return Response.ok().entity(comment).type(MediaType.APPLICATION_JSON).build();
        } catch (SQLException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            return Response.notModified().type(MediaType.APPLICATION_JSON_TYPE).build();
        }
    }

    public Response deleteComment(int commentId, String creator)  {
        if(log.isDebugEnabled()){
            log.debug("Executing deleteComment, deleted by: " + creator);
        }

        CommentDAO commentDAO = DAODeligate.getCommentInstance();
        boolean deleted = false;
        try {
            if(commentDAO.isOwnerOfComment(commentId, creator)) {
                deleted = commentDAO.deleteCommentByCommentId(commentId, creator);
                return deleted ? Response.ok(deleted).build() : Response.notModified().build();
            } else {
                return Response.status(Response.Status.UNAUTHORIZED).type(MediaType.APPLICATION_JSON_TYPE).build();
            }
        } catch (SQLException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).type(MediaType.APPLICATION_JSON_TYPE).build();
        }
    }

    public Response editComment(Comment comment)  {
        if(log.isDebugEnabled()){
            log.debug("Executing editComment, edited by: " + comment.getCreator());
        }

        CommentDAO commentDAO = DAODeligate.getCommentInstance();
        boolean edited = false;
        try {
            if(commentDAO.isOwnerOfComment(comment.getId(), comment.getCreator())){
                edited = commentDAO.editComment(comment);
                return edited ? Response.ok(edited).build() : Response.notModified().build();
            } else {
                return Response.status(Response.Status.UNAUTHORIZED).type(MediaType.APPLICATION_JSON_TYPE).build();
            }
        } catch (SQLException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).type(MediaType.APPLICATION_JSON_TYPE).build();
        }
    }

    public String sayHello(){
        System.out.println("Helllooooood");
        return "hello";
    }
}
