package org.wso2.carbon.issue.tracker.delegate;

import org.apache.commons.collections.bag.SynchronizedBag;
import org.wso2.carbon.issue.tracker.dao.CommentDAO;
import org.wso2.carbon.issue.tracker.dao.IssueDAO;
import org.wso2.carbon.issue.tracker.dao.ProjectDAO;
import org.wso2.carbon.issue.tracker.dao.VersionDAO;
import org.wso2.carbon.issue.tracker.dao.impl.CommentDAOImpl;


public class DAODeligate {
    private static CommentDAO commentInstance = null;
    private static IssueDAO issueInstance = null;
    private static ProjectDAO projectInstance = null;
    private static VersionDAO versionInstance= null;

    public synchronized static CommentDAO getCommentInstance(){
        if(commentInstance == null)
            commentInstance = new CommentDAOImpl();
        return commentInstance;
    }

    public synchronized static IssueDAO getIssueInstance(){
        if(issueInstance == null)
            issueInstance = new IssueDAO();
        return issueInstance;
    }

    public synchronized static ProjectDAO getProjectInstance(){
        if(projectInstance == null)
            projectInstance = new ProjectDAO();
        return projectInstance;
    }

    public synchronized static VersionDAO getVersionInstance(){
        if(versionInstance == null)
            versionInstance = new VersionDAO();
        return versionInstance;
    }

}
