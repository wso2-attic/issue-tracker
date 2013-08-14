package org.wso2.carbon.issue.tracker.server;

import org.wso2.carbon.issue.tracker.bean.Comment;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: nihanth
 * Date: 8/13/13
 * Time: 11:15 AM
 * To change this template use File | Settings | File Templates.
 */
@Path("/commentservice")
public interface CommentService {
    @GET
    @Path("/issue/{issueId}/")
    @Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
    public List<Comment> getCommentsForIssue(@PathParam("issueId") int issueId) throws Exception;

    @POST
    @Path("/postcomment")
    @Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    @Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
    public Response postCommentForIssue(Comment comment) throws Exception;

    @DELETE
    @Path("/deletecomment")
    @Produces(MediaType.APPLICATION_JSON)
    public String deleteComment(@FormParam("usreId") int userId,
                                @FormParam("commentId") int commentId) throws Exception;

    @POST
    @Path("/editcomment")
    @Consumes(MediaType.APPLICATION_JSON)
    public String editComment(Comment comment) throws Exception;

    @GET
    @Path("/hello")
    public String sayHello();

}
