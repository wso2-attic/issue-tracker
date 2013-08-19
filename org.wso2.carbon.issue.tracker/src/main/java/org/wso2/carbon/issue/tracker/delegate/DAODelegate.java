package org.wso2.carbon.issue.tracker.delegate;

import org.wso2.carbon.issue.tracker.dao.CommentDAO;
import org.wso2.carbon.issue.tracker.dao.IssueDAO;
import org.wso2.carbon.issue.tracker.dao.ProjectDAO;
import org.wso2.carbon.issue.tracker.dao.VersionDAO;
import org.wso2.carbon.issue.tracker.dao.impl.CommentDAOImpl;
import org.wso2.carbon.issue.tracker.dao.impl.IssueDAOImpl;
import org.wso2.carbon.issue.tracker.dao.impl.ProjectDAOImpl;
import org.wso2.carbon.issue.tracker.dao.impl.VersionDAOImpl;


/**
 * Singleton class to get DAO object
 */
public class DAODelegate {

    private static CommentDAO commentInstance = new CommentDAOImpl();
    private static IssueDAO issueInstance = new IssueDAOImpl();
    private static ProjectDAO projectInstance = new ProjectDAOImpl();
    private static VersionDAO versionInstance = new VersionDAOImpl();

    /**
     * Get CommentDAO object
     * @return {@link CommentDAO}
     */
    public static CommentDAO getCommentInstance() {
        return commentInstance;
    }

    /**
     * Get IssueDAOImpl object
     * @return  {@link IssueDAOImpl}
     */
    public static IssueDAO getIssueInstance() {
        return issueInstance;
    }

    /**
     * Get ProjectDAO object
     * @return {@link ProjectDAO}
     */
    public static ProjectDAO getProjectInstance() {
        return projectInstance;
    }

    /**
     * Get VersionDAO object
     * @return {@link VersionDAO}
     */
    public static VersionDAO getVersionInstance() {
        return versionInstance;
    }

}
