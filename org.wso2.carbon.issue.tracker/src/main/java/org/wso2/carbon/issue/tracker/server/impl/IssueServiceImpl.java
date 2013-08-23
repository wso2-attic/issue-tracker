package org.wso2.carbon.issue.tracker.server.impl;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.cxf.common.util.StringUtils;
import org.wso2.carbon.issue.tracker.bean.*;
import org.wso2.carbon.issue.tracker.dao.CommentDAO;
import org.wso2.carbon.issue.tracker.dao.IssueDAO;
import org.wso2.carbon.issue.tracker.dao.SearchDAO;
import org.wso2.carbon.issue.tracker.delegate.DAODelegate;
import org.wso2.carbon.issue.tracker.server.IssueService;
import org.wso2.carbon.issue.tracker.util.Constants;

import javax.ws.rs.PathParam;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.xml.bind.JAXBElement;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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
            return Response.ok().entity(response).type(MediaType.APPLICATION_JSON_TYPE).build();
        } catch (Exception e) {
            String msg = "Error while get comments for issue";
            log.error(msg, e);
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).type(MediaType.APPLICATION_JSON_TYPE).build();
        }
    }


    /**
     * Edit issue details
     * @param tenantDomain      Tenant domain name
     * @param uniqueKey         Unique key of issue which need to retrieve
     * @param issue             {@link Issue}
     * @return                  {@link Response}
     */
    @Override
    public Response editIssue(String tenantDomain, String  uniqueKey, Issue issue) {
        if (log.isDebugEnabled()) {
            log.debug("Executing editIssue, created by: " + issue.getOwner());
        }

        if (StringUtils.isEmpty(issue.getSummary())) {
            return Response.status(Response.Status.BAD_REQUEST).entity("Issue summary cannot be empty!").build();
        }

        if (StringUtils.isEmpty(issue.getOwner())) {
            return Response.status(Response.Status.BAD_REQUEST).entity("Issue owner cannot be empty!").build();
        }

        if(StringUtils.isEmpty(issue.getType())){
            return Response.status(Response.Status.BAD_REQUEST).entity("Issue Type cannot be empty!").build();
        }

        if(StringUtils.isEmpty(issue.getPriority())){
            issue.setPriority("NORMAL");
        }

        if(StringUtils.isEmpty(issue.getStatus())){
            issue.setStatus("OPEN");
        }

        if(StringUtils.isEmpty(issue.getType())){
            return Response.status(Response.Status.BAD_REQUEST).entity("Issue Type cannot be empty!").build();
        }

        if(StringUtils.isEmpty(issue.getType())){
            return Response.status(Response.Status.BAD_REQUEST).entity("Issue Type cannot be empty!").build();
        }
        issue.setKey(uniqueKey);
        IssueDAO issueDAO = DAODelegate.getIssueInstance();
        try {
            boolean isInserted = issueDAO.update(issue);
            if (isInserted)
                return Response.ok(isInserted).type(MediaType.APPLICATION_JSON).build();
            else
                return Response.notModified().type(MediaType.APPLICATION_JSON_TYPE).entity("Issue is not successfully updated.").build();
        } catch (SQLException e) {
            String msg = "Error while edit Issue to Project";
            log.error(msg, e);
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).type(MediaType.APPLICATION_JSON_TYPE).build();
        }
    }


    /**
     * Add new comment to given issue
     * @param tenantDomain  Tenant domain name
     * @param uniqueKey     Comment's, issue id
     * @param comment       {@link Comment}
     * @return              {@link Response}, Returns HTTP/1.1 200 for successfully added comment else returns internal server error HTTP/1.1 500
     */
    @Override
    public Response addNewCommentForIssue(String tenantDomain, String uniqueKey, Comment comment) {
        if (log.isDebugEnabled()) {
            log.debug("Executing addNewCommentForIssue, created by: " + comment.getCreator());
        }

        if (StringUtils.isEmpty(comment.getCommentDescription())) {
            return Response.status(Response.Status.BAD_REQUEST).entity("Comment cannot be empty").build();
        }

        if (StringUtils.isEmpty(comment.getCreator())) {
            return Response.status(Response.Status.BAD_REQUEST).entity("Comment creator cannot be empty").build();
        }

        CommentDAO commentDAO = DAODelegate.getCommentInstance();
        try {
            boolean isInserted = commentDAO.addCommentForIssue(comment, uniqueKey);
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
     * @param uniqueKey      Issue id of comment, which need to edit
     * @param commentId      Comment id
     * @param comment        {@link Comment}
     * @return               {@link Response}, Returns HTTP/1.1 200 for successfully edited comment else
     *                       returns internal server error HTTP/1.1 500
     */
    @Override
    public Response modifyCommentForIssue(String tenantDomain, String uniqueKey, int commentId, Comment comment) {
        if(log.isDebugEnabled()){
            log.debug("Executing modifyCommentForIssue, CommentId: " + commentId);
        }

        if (StringUtils.isEmpty(comment.getCommentDescription())) {
            return Response.status(Response.Status.BAD_REQUEST).entity("Comment cannot be empty").build();
        }

        if (StringUtils.isEmpty(comment.getCreator())) {
            return Response.status(Response.Status.BAD_REQUEST).entity("Comment creator cannot be empty").build();
        }

        if (commentId == 0) {
            return Response.status(Response.Status.BAD_REQUEST).entity("Invalid comment ID").build();
        }

        CommentDAO commentDAO = DAODelegate.getCommentInstance();
        try {
            comment.setId(commentId);
            boolean isUpdated = commentDAO.editComment(comment, uniqueKey);
            if(isUpdated)
                return Response.ok().build();
            else
                return Response.status(Response.Status.BAD_REQUEST).type(MediaType.APPLICATION_JSON_TYPE).entity("Data is not successfully updated.").build();
        } catch (SQLException e) {
            String msg = "Error while edit comments";
            log.error(msg, e);
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).type(MediaType.APPLICATION_JSON_TYPE).build();
        }
    }


    /**
     * Deleted comment from DB
     * @param tenantDomain      Tenant domain name
     * @param uniqueKey         Issue id of comment, which need to delete
     * @param commentId         Comment id of comment, which need to delete
     * @return                  {@link Response}, Returns HTTP/1.1 200 for successfully edited comment else
     *                          returns internal server error HTTP/1.1 500
     */
    @Override
    public Response deleteComment(String tenantDomain, String uniqueKey, int commentId) {
        if(log.isDebugEnabled()){
            log.debug("Executing deleteComment, commentID: " + commentId);
        }

        if (commentId == 0) {
            return Response.status(Response.Status.BAD_REQUEST).entity("Invalid comment ID").build();
        }

        CommentDAO commentDAO = DAODelegate.getCommentInstance();
        try {
            boolean result = commentDAO.deleteCommentByCommentId(uniqueKey, commentId);
            return result ? Response.ok().build() : Response.notModified("Invalid credentials").build();
        } catch (SQLException e) {
            String msg = "Error while delete comments";
            log.error(msg, e);
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).type(MediaType.APPLICATION_JSON_TYPE).build();
        }
    }

    /**
     * Search Issues of given project
     * @param tenantDomain    Tenant domain name
     * @param searchBean      {@link SearchBean}
     * @return
     */
    @Override
    public Response searchIssue(String tenantDomain, SearchBean searchBean) {
        SearchDAO searchDAO = DAODelegate.getSerarchInstance();
        List<SearchResponse> list = null;

        String status = searchBean.getIssueStatus();
        String issueType = searchBean.getIssueType();
        String priority = searchBean.getPriority();
        String severity = searchBean.getSeverity();

        if(StringUtils.isEmpty(status) || status.equals("-1"))
            searchBean.setIssueStatus(null);
        if(StringUtils.isEmpty(issueType) || issueType.equals("-1"))
            searchBean.setIssueType(null);
        if(StringUtils.isEmpty(priority) || priority.equals("-1"))
            searchBean.setPriority(null);
        if(StringUtils.isEmpty(severity) || severity.equals("-1"))
            searchBean.setSeverity(null);

        try {
            if(searchBean.getSearchType() == Constants.ALL_ISSUE)
                list = searchDAO.searchIssueBySummaryContent(searchBean);
            else
                list = searchDAO.searchIssue(searchBean);

        } catch (Exception e) {
            String msg = "Error while searching Issues";
            log.error(msg, e);
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).type(MediaType.APPLICATION_JSON_TYPE).build();
        }
        GenericEntity entity = new GenericEntity<List<SearchResponse>>(list){};
        return Response.ok().entity(entity).type(MediaType.APPLICATION_JSON_TYPE).build();
    }
}
