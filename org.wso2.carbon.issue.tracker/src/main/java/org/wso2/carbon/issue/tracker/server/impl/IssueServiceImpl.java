package org.wso2.carbon.issue.tracker.server.impl;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.wso2.carbon.issue.tracker.bean.Comment;
import org.wso2.carbon.issue.tracker.bean.Issue;
import org.wso2.carbon.issue.tracker.dao.CommentDAO;
import org.wso2.carbon.issue.tracker.delegate.DAODelegate;
import org.wso2.carbon.issue.tracker.server.IssueService;

import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.sql.SQLException;
import java.util.List;

/**
 * Implementation of {@link IssueService}
 *
 */
public class IssueServiceImpl implements IssueService {

    private static final Log log = LogFactory.getLog(IssueServiceImpl.class);

    /**
     * Get issues and comments for a given issue id
     * @param tenantDomain      Tenant domain name
     * @param issueId           Issue id which need to retrieve
     * @return                  {@link Response}
     */
    @Override
    public Response getIssue(String tenantDomain, int issueId) {

        if(log.isDebugEnabled()){
            log.debug("Executing getIssue, issueId: " + issueId);
        }

        // TODO: need to get issue object and append response

        CommentDAO commentDAO = DAODelegate.getCommentInstance();
        List<Comment> comments = null;
        try {
            // get all comments related to given issue
            comments = commentDAO.getCommentsForIssue(issueId);
        } catch (Exception e) {
            String msg = "Error while get comments for issue";
            log.error(msg, e);
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).type(MediaType.APPLICATION_JSON_TYPE).build();
        }

        GenericEntity<List<Comment>> entity = new GenericEntity<List<Comment>>(comments){} ;
        return Response.ok().entity(entity).type(MediaType.APPLICATION_JSON_TYPE).build();
    }


    /**
     * Edit issue details
     * @param tenantDomain      Tenant domain name
     * @param issueId           Issue id of issue which need to edit
     * @param issue             {@link Issue}
     * @return                   {@link Response}
     */
    @Override
    public Response editIssue(String tenantDomain, int issueId, Issue issue) {
        // TODO: DB call to edit issue
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }


    /**
     * Add new comment to given issue
     * @param tenantDomain  Tenant domain name
     * @param issueId       Comment's, issue id
     * @param comment       {@link Comment}
     * @return              {@link Response}, Returns HTTP/1.1 200 for successfully added comment else returns internal server error HTTP/1.1 500
     */
    @Override
    public Response addNewCommentForIssue(String tenantDomain, int issueId, Comment comment) {
        if (log.isDebugEnabled()) {
            log.debug("Executing addNewCommentForIssue, created by: " + comment.getCreator());
        }
        CommentDAO commentDAO = DAODelegate.getCommentInstance();
        try {
            comment.setIssueId(issueId);
            commentDAO.addCommentForIssue(comment);
            return Response.ok().type(MediaType.APPLICATION_JSON).build();
        } catch (SQLException e) {
            String msg = "Error while add comments for issue";
            log.error(msg, e);
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).type(MediaType.APPLICATION_JSON_TYPE).build();
        }
    }


    /**
     * Edit comment to given issue
     *  @param tenantDomain  Tenant domain name
     * @param issueId        Issue id of comment, which need to edit
     * @param commentId      Comment id
     * @param comment        {@link Comment}
     * @return               {@link Response}, Returns HTTP/1.1 200 for successfully edited comment else
     *                       returns internal server error HTTP/1.1 500
     */
    @Override
    public Response modifyCommentForIssue(String tenantDomain, int issueId, int commentId, Comment comment) {
        if(log.isDebugEnabled()){
            log.debug("Executing modifyCommentForIssue, CommentId: " + commentId);
        }

        CommentDAO commentDAO = DAODelegate.getCommentInstance();
        try {
            comment.setIssueId(issueId);
            comment.setId(commentId);
            commentDAO.editComment(comment);
            return Response.ok().build();

        } catch (SQLException e) {
            String msg = "Error while edit comments";
            log.error(msg, e);
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).type(MediaType.APPLICATION_JSON_TYPE).build();
        }
    }


    /**
     * Deleted comment from DB
     * @param tenantDomain      Tenant domain name
     * @param issueId           Issue id of comment, which need to delete
     * @param commentId         Comment id of comment, which need to delete
     * @return                  {@link Response}, Returns HTTP/1.1 200 for successfully edited comment else
     *                          returns internal server error HTTP/1.1 500
     */
    @Override
    public Response deleteComment(String tenantDomain, int issueId, int commentId) {
        if(log.isDebugEnabled()){
            log.debug("Executing deleteComment, commentID: " + commentId);
        }

        CommentDAO commentDAO = DAODelegate.getCommentInstance();
        try {
            boolean result = commentDAO.deleteCommentByCommentId(issueId, commentId);
            return result ? Response.ok().build() : Response.notModified("Invalid credentials").build();

        } catch (SQLException e) {
            String msg = "Error while delete comments";
            log.error(msg, e);
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).type(MediaType.APPLICATION_JSON_TYPE).build();
        }
    }
}
