package org.wso2.carbon.issue.tracker.server.impl;

import org.wso2.carbon.issue.tracker.bean.Issue;
import org.wso2.carbon.issue.tracker.bean.Project;
import org.wso2.carbon.issue.tracker.server.ProjectService;

import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;


public class ProjectServiceImpl implements ProjectService{
    @Override
    public Response getAllProject() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public Response getProject(@PathParam("projectId") int projectId) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public Response addProject(Project project) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public Response editProject(@PathParam("projectId") int projectId, Project project) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public Response getAllVersionsOfProject(@PathParam("projectId") int projectId) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public Response getAllIssuesOfProject(@PathParam("projectId") int projectId) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public Response addNewIssueToProject(@PathParam("projectId") int projectId, Issue issue) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }
}
