package org.wso2.carbon.issue.tracker.dao;

import java.sql.SQLException;
import java.util.List;

import org.wso2.carbon.issue.tracker.bean.Comment;
import org.wso2.carbon.issue.tracker.bean.Issue;
import org.wso2.carbon.issue.tracker.bean.IssueResponse;

/**
 * Defines the database operations for a {@link Issue}
 */
public interface IssueDAO {


    /**
     *
     * @param issue {@link Issue}
     * @return      Issue unique key
     * @throws SQLException
     */
    public String add(Issue issue, String projectKey, int tenantId) throws SQLException;

    /**
     *
     * @param issue  {@link Issue}
     * @return       Is issue is successfully updated or not
     * @throws SQLException
     */
    public boolean update(Issue issue) throws SQLException;

    /**
     * Any column of the issue can be updated using this method. REMEBER!! This
     * is to use only one attribute of the issue is changed
     *
     * @param issue
     *            {@link Issue}
     * @param columnName
     *            column name
     * @param value
     *            new value
     * @return    Is attribute is successfully updated or not
     */
    public boolean updateAttribute(Issue issue, String columnName, String value) throws SQLException;

    /**
     *
     * @param uniqueKey
     * @return
     * @throws SQLException
     */
    public IssueResponse getIssueByKey(String uniqueKey) throws SQLException;

    /**
     *
     * @param id  Issue ID
     * @return    {@link Issue}
     * @throws SQLException
     */
    public Issue getIssueById(int id) throws SQLException;

    /**
     *
     * @param projectKey
     * @return  {@link List<Issue>}
     * @throws SQLException
     */
    public List<IssueResponse> getAllIssuesOfProject(String projectKey, int tenantId) throws SQLException;

}
