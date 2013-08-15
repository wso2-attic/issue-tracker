package org.wso2.carbon.issue.tracker.delegate;

import org.wso2.carbon.issue.tracker.dao.CommentDAO;
import org.wso2.carbon.issue.tracker.dao.ProjectDAO;
import org.wso2.carbon.issue.tracker.dao.VersionDAO;
import org.wso2.carbon.issue.tracker.dao.impl.CommentDAOImpl;
import org.wso2.carbon.issue.tracker.dao.impl.IssueDAO;
import org.wso2.carbon.issue.tracker.dao.impl.ProjectDAOImpl;
import org.wso2.carbon.issue.tracker.dao.impl.VersionDAOImpl;


/**
 * Singleton class to get DAO object
 */
public class DAODelegate {

    private static CommentDAO commentInstance = null;
    private static IssueDAO issueInstance = null;
    private static ProjectDAO projectInstance = null;
    private static VersionDAO versionInstance = null;

    /**
     *
     * @return
     */
    public synchronized static CommentDAO getCommentInstance() {
        if (commentInstance == null)
            commentInstance = new CommentDAOImpl();
        return commentInstance;
    }

    public synchronized static IssueDAO getIssueInstance() {
        if (issueInstance == null)
            issueInstance = new IssueDAO();
        return issueInstance;
    }

    public synchronized static ProjectDAO getProjectInstance() {
        if (projectInstance == null)
            projectInstance = new ProjectDAOImpl();
        return projectInstance;
    }

    public synchronized static VersionDAO getVersionInstance() {
        if (versionInstance == null)
            versionInstance = new VersionDAOImpl();
        return versionInstance;
    }

}
