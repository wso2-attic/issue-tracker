package org.wso2.carbon.issue.tracker.server.impl;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.cxf.common.util.StringUtils;
import org.wso2.carbon.issue.tracker.bean.Comment;
import org.wso2.carbon.issue.tracker.bean.Issue;
import org.wso2.carbon.issue.tracker.bean.IssueResponse;
import org.wso2.carbon.issue.tracker.dao.CommentDAO;
import org.wso2.carbon.issue.tracker.dao.IssueDAO;
import org.wso2.carbon.issue.tracker.delegate.DAODelegate;
import org.wso2.carbon.issue.tracker.server.IssueService;

import javax.ws.rs.WebApplicationException;
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
     * @param uniqueKey         Unique key of issue which need to retrieve
     * @return                  {@link Response}
     */
    @Override
    public Response getIssue(String tenantDomain, String uniqueKey) {

        if(log.isDebugEnabled()){
            log.debug("Executing getIssue, uniqueKey: " + uniqueKey);
        }
        IssueDAO issueDAO = DAODelegate.getIssueInstance();
        CommentDAO commentDAO = DAODelegate.getCommentInstance();

        List<Comment> comments = null;
        try {
            Issue issue = issueDAO.getIssueByKey(uniqueKey);

            if(issue!=null)
                comments = commentDAO.getCommentsForIssue(issue.getId());     // get all comments related to given issue

            IssueResponse response = new IssueResponse();
            response.setIssue(issue);
            response.setComments(comments);

            System.out.println(comments.size());

            GenericEntity<List<Comment>> entity = new GenericEntity<List<Comment>>(comments){} ;
            return Response.ok().entity(response).type(MediaType.APPLICATION_JSON_TYPE).build();


            //return Response.ok().entity(response).type(MediaType.APPLICATION_JSON_TYPE).build();
            //return response;
        } catch (Exception e) {
            String msg = "Error while get comments for issue";
            log.error(msg, e);
            //throw new WebApplicationException(e, Response.Status.INTERNAL_SERVER_ERROR);
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).type(MediaType.APPLICATION_JSON_TYPE).build();
        }
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

        if (StringUtils.isEmpty(comment.getCommentDescription())) {
            return Response.status(Response.Status.BAD_REQUEST).entity("Comment cannot be empty").build();
        }

        if (StringUtils.isEmpty(comment.getCreator())) {
            return Response.status(Response.Status.BAD_REQUEST).entity("Comment creator cannot be empty").build();
        }

        if (issueId ==0) {
            return Response.status(Response.Status.BAD_REQUEST).entity("Invalid Issue ID").build();
        }

        CommentDAO commentDAO = DAODelegate.getCommentInstance();
        try {
            boolean isInserted = commentDAO.addCommentForIssue(comment, issueId);
            if (isInserted)
                return Response.ok().type(MediaType.APPLICATION_JSON).build();
            else
                return Response.notModified().type(MediaType.APPLICATION_JSON_TYPE).entity("Data is not successfully inserted.").build();
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

        if (StringUtils.isEmpty(comment.getCommentDescription())) {
            return Response.status(Response.Status.BAD_REQUEST).entity("Comment cannot be empty").build();
        }

        if (StringUtils.isEmpty(comment.getCreator())) {
            return Response.status(Response.Status.BAD_REQUEST).entity("Comment creator cannot be empty").build();
        }

        if (issueId == 0) {
            return Response.status(Response.Status.BAD_REQUEST).entity("Invalid Issue ID").build();
        }

        if (commentId == 0) {
            return Response.status(Response.Status.BAD_REQUEST).entity("Invalid comment ID").build();
        }

        CommentDAO commentDAO = DAODelegate.getCommentInstance();
        try {
            comment.setId(commentId);
            boolean isUpdated = commentDAO.editComment(comment, issueId);
            if(isUpdated)
                return Response.ok().build();
            else
                return Response.notModified().type(MediaType.APPLICATION_JSON_TYPE).entity("Data is not successfully updated.").build();

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

        if (issueId == 0) {
            return Response.status(Response.Status.BAD_REQUEST).entity("Invalid Issue ID").build();
        }

        if (commentId == 0) {
            return Response.status(Response.Status.BAD_REQUEST).entity("Invalid comment ID").build();
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
