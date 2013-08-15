package org.wso2.carbon.issue.tracker.server;

import org.wso2.carbon.issue.tracker.bean.Comment;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/commentservice")
public interface CommentService {
    @GET
    @Path("/issue/{issueId}/")
    @Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
    public Response getCommentsForIssue(@PathParam("issueId") int issueId) ;

    @POST
    @Path("/postcomment")
    @Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    @Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
    public Response postCommentForIssue(Comment comment) ;

    @DELETE
    @Path("/deletecomment/{commentId}/{creator}/")
    @Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
    public Response deleteComment(@PathParam("commentId") int commentId, @PathParam("creator") String creator) ;

    @POST
    @Path("/editcomment")
    @Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    @Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
    public Response editComment(Comment comment) ;

    @GET
    @Path("/hello")
    public String sayHello();

}
