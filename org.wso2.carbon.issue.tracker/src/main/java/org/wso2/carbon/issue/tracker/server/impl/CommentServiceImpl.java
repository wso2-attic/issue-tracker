package org.wso2.carbon.issue.tracker.server.impl;

import org.wso2.carbon.issue.tracker.bean.Comment;
import org.wso2.carbon.issue.tracker.dao.CommentDAO;
import org.wso2.carbon.issue.tracker.dao.impl.CommentDAOImpl;
import org.wso2.carbon.issue.tracker.server.CommentService;

import javax.ws.rs.*;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: nihanth
 * Date: 8/14/13
 * Time: 1:49 PM
 * To change this template use File | Settings | File Templates.
 */
public class CommentServiceImpl implements CommentService {

    public List<Comment> getCommentsForIssue(@PathParam("issueId") int issueId) throws Exception {
        CommentDAO commentDAO = new CommentDAOImpl();
        List<Comment> comments = commentDAO.getCommentsForIssue(issueId);

        GenericEntity<List<Comment>> entity = new GenericEntity<List<Comment>>(comments){} ;
        return entity.getEntity();
    }

    public Response postCommentForIssue(Comment comment) throws Exception{
        System.out.println("comment " + comment.getComment());
        System.out.println("issue id: " + comment.getIssueId());

        CommentDAO commentDAO = new CommentDAOImpl();
        commentDAO.postCommentForIssue(comment);

        return Response.ok().entity(comment).type(MediaType.APPLICATION_JSON).build();
    }

    public String deleteComment(@FormParam("usreId") int userId,
                                @FormParam("commentId") int commentId) throws Exception {
        CommentDAO commentDAO = new CommentDAOImpl();
        commentDAO.deleteCommentByCommentId(userId, commentId);
        // TODO owner of comment can delete
        return null;
    }

    public String editComment(Comment comment) throws Exception {
        CommentDAO commentDAO = new CommentDAOImpl();
        commentDAO.editComment(comment);
        // TODO owner of comment can edit
        return null;
    }

    public String sayHello(){
        System.out.println("Helllooooood");
        return "hello";
    }
}
