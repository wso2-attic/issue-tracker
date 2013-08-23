package org.wso2.carbon.issue.tracker.dao;

import java.sql.SQLException;
import java.util.List;

import org.wso2.carbon.issue.tracker.bean.Comment;
import org.wso2.carbon.issue.tracker.bean.Issue;

public interface IssueDAO {

    
    public boolean add(Issue issue) throws SQLException;

    public boolean update(Issue issue) throws SQLException;
    
    public boolean updateAttribute(Issue issue, String columnName, String value) throws SQLException;
    
    public void addComment(Issue issue, Comment comment) throws SQLException;
    
    public Issue getIssueByKey(String uniqueKey) throws SQLException;
    
    public Issue getIssueById(int id) throws SQLException;

    public List<Issue> getAllIssuesOfProject(int projectId) throws SQLException;

}
