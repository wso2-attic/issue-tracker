package org.wso2.carbon.issue.tracker.server;

import org.wso2.carbon.issue.tracker.bean.Comment;
import org.wso2.carbon.issue.tracker.bean.Issue;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;


/**
 *
 * Service class defines, operations related to Issue related services
 *
 */

@Path("/{tenantDomain}/issue")
public interface IssueService {

    @GET
    @Path("/{uniqueKey}")
    @Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
    public Response getIssue(@PathParam("tenantDomain") String tenantDomain, @PathParam("uniqueKey") String uniqueKey);

    @POST
    @Path("/{issueId}")
    @Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    @Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
    public Response editIssue(@PathParam("tenantDomain") String tenantDomain, @PathParam("issueId") int issueId, Issue issue);

    @POST
    @Path("/{issueId}/comment")
    @Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    @Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
    public Response addNewCommentForIssue(@PathParam("tenantDomain") String tenantDomain, @PathParam("issueId") int issueId, Comment comment);

    @POST
    @Path("/{issueId}/comment/{commentId}")
    @Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    @Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
    public Response modifyCommentForIssue(@PathParam("tenantDomain") String tenantDomain, @PathParam("issueId") int issueId, @PathParam("commentId") int commentId, Comment comment);

    @DELETE
    @Path("/{issueId}/comment/{commentId}/")
    @Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
    public Response deleteComment(@PathParam("tenantDomain") String tenantDomain, @PathParam("issueId") int issueId, @PathParam("commentId") int commentId);

}
