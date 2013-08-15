package org.wso2.carbon.issue.tracker.server.impl;

import org.wso2.carbon.issue.tracker.bean.Issue;
import org.wso2.carbon.issue.tracker.bean.Project;
import org.wso2.carbon.issue.tracker.server.ProjectService;

import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;

/**
 * Implementation of {@link ProjectService}
 *
 */
public class ProjectServiceImpl implements ProjectService{
    @Override
    public Response getAllProject(String tenantDomain) {
        System.out.println("HEHEHHE");
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public Response getProject(String tenantDomain, int projectId) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public Response addProject(String tenantDomain, Project project) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public Response editProject(String tenantDomain, int projectId, Project project) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public Response getAllVersionsOfProject(String tenantDomain, int projectId) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public Response getAllIssuesOfProject(String tenantDomain, int projectId) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public Response addNewIssueToProject(String tenantDomain, int projectId, Issue issue) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }
}
