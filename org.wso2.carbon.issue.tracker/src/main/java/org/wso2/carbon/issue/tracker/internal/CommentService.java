package org.wso2.carbon.issue.tracker.internal;

import org.wso2.carbon.issue.tracker.bean.Comment;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: nihanth
 * Date: 8/13/13
 * Time: 11:15 AM
 * To change this template use File | Settings | File Templates.
 */
@Path("/commentservice")
public class CommentService {
    @GET
    @Path("/issue/{id}/")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Comment> getCommentsForIssue(@PathParam("id") String id) throws Exception {
        // return Response.status(200).entity("addUser is called, userAgent : " + userAgent).build();
        return null;
    }

    @POST
    @Path("/postcomment")
    @Consumes(MediaType.APPLICATION_JSON)
    public String postCommentForIssue(Comment comment) throws Exception{

        return null;
    }

    @DELETE
    @Path("/deletecomment")
    @Produces(MediaType.APPLICATION_JSON)
    public String deleteComment(@FormParam("usreId") int usreId,
                                @FormParam("commentId") int commentId) throws Exception {
        // TODO owner of comment can delete
        return null;
    }

    @POST
    @Path("/editcomment")
    @Consumes(MediaType.APPLICATION_JSON)
    public String editComment(Comment comment) throws Exception {
        // TODO owner of comment can edit
        return null;
    }
}
