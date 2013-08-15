package org.wso2.carbon.issue.tracker.server.impl;

import org.wso2.carbon.issue.tracker.bean.Comment;
import org.wso2.carbon.issue.tracker.bean.Issue;
import org.wso2.carbon.issue.tracker.server.IssueService;

import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;


public class IssueServiceImpl implements IssueService {
    @Override
    public Response getIssue(@PathParam("issueId") int issueId) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public Response editIssue(@PathParam("issueId") int issueId, Issue issue) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public Response addNewCommentForIssue(@PathParam("issueId") int issueId, Comment comment) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public Response modifyCommentForIssue(@PathParam("issueId") int issueId, @PathParam("commentId") int commentId, Comment comment) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public Response deleteComment(@PathParam("issueId") int issueId, @PathParam("commentId") int commentId) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }
}
