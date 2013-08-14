package org.wso2.carbon.issue.tracker.internal;

import org.wso2.carbon.issue.tracker.bean.Comment;
import org.wso2.carbon.issue.tracker.dao.CommentDAO;
import org.wso2.carbon.issue.tracker.dao.impl.CommentDAOImpl;

import javax.ws.rs.*;
import javax.ws.rs.core.GenericEntity;
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
    @Path("/issue/{issueId}/")
    @Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
    public List<Comment> getCommentsForIssue(@PathParam("issueId") int issueId) throws Exception {
        CommentDAO commentDAO = new CommentDAOImpl();
        List<Comment> comments = commentDAO.getCommentsForIssue(issueId);
        // return Response.status(200).entity("addUser is called, userAgent : " + userAgent).build();
        System.out.println(">>>>>>" + comments.size());
        for(Comment c:comments){
            System.out.println(c.getCommentId());
        }

        GenericEntity<List<Comment>> entity = new GenericEntity<List<Comment>>(comments){} ;
        return entity.getEntity();
    }

    @POST
    @Path("/postcomment")
    @Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    @Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
    public String postCommentForIssue(Comment comment) throws Exception{
        System.out.println("comment " + comment.getComment());
        //CommentDAO commentDAO = new CommentDAOImpl();
        //commentDAO.postCommentForIssue(comment);
        return null;
    }



    @DELETE
    @Path("/deletecomment")
    @Produces(MediaType.APPLICATION_JSON)
    public String deleteComment(@FormParam("usreId") int userId,
                                @FormParam("commentId") int commentId) throws Exception {
        CommentDAO commentDAO = new CommentDAOImpl();
        commentDAO.deleteCommentByCommentId(userId, commentId);
        // TODO owner of comment can delete
        return null;
    }

    @POST
    @Path("/editcomment")
    @Consumes(MediaType.APPLICATION_JSON)
    public String editComment(Comment comment) throws Exception {
        CommentDAO commentDAO = new CommentDAOImpl();
        commentDAO.editComment(comment);
        // TODO owner of comment can edit
        return null;
    }

    @GET
    @Path("/hello")
    public String sayHello(){
        System.out.println("Helllooooood");
        return "hello";
    }
}
