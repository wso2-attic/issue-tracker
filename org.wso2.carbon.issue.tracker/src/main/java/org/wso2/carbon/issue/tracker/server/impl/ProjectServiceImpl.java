package org.wso2.carbon.issue.tracker.server.impl;

import java.sql.SQLException;
import java.util.List;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.apache.cxf.common.util.StringUtils;
import org.wso2.carbon.issue.tracker.bean.Issue;
import org.wso2.carbon.issue.tracker.bean.Project;
import org.wso2.carbon.issue.tracker.bean.Version;
import org.wso2.carbon.issue.tracker.delegate.DAODelegate;
import org.wso2.carbon.issue.tracker.server.ProjectService;
import org.wso2.carbon.issue.tracker.util.IssueTrackerException;

public class ProjectServiceImpl implements ProjectService {
    @Override
    public Response getAllProject(String tenantDomain) {
        return null;
    }

    @Override
    public Response getProject(String tenantDomain, int projectId) {

        try {
            Project project = DAODelegate.getProjectInstance().get(projectId);
            Response response = null;
            if (project != null) {
                response = Response.ok().entity(project).build();
            } else {
                response = Response.status(Status.NOT_FOUND).build();
            }
            return response;
        } catch (SQLException e) {
            throw new WebApplicationException(e, Response.Status.INTERNAL_SERVER_ERROR);
        }

    }

    @Override
    public Response addProject(String tenantDomain, Project project) {

        if (StringUtils.isEmpty(project.getName())) {
            throw new WebApplicationException(
                                              new IllegalArgumentException(
                                                                           "project name cannot be empty"));
        }

        if (StringUtils.isEmpty(project.getOwner())) {
            throw new WebApplicationException(
                                              new IllegalArgumentException(
                                                                           "project owner cannot be empty"));
        }

        if ((project.getOrganizationId() <= 0)) {
            throw new WebApplicationException(
                                              new IllegalArgumentException(
                                                                           "invalid organization id"));
        }

        try {
            DAODelegate.getProjectInstance().add(project);
        } catch (SQLException e) {
            throw new WebApplicationException(e, Response.Status.INTERNAL_SERVER_ERROR);
        }

        return Response.ok().build();
    }

    @Override
    public Response editProject(String tenantDomain, int projectId, Project project) {

        if (StringUtils.isEmpty(project.getName())) {
            throw new WebApplicationException(
                                              new IllegalArgumentException(
                                                                           "project name cannot be empty"));
        }

        if (StringUtils.isEmpty(project.getOwner())) {
            throw new WebApplicationException(
                                              new IllegalArgumentException(
                                                                           "project owner cannot be empty"));
        }

        if ((project.getOrganizationId() <= 0)) {
            throw new WebApplicationException(
                                              new IllegalArgumentException(
                                                                           "invalid organization id"));
        }

        try {
            project.setId(projectId);
            DAODelegate.getProjectInstance().update(project);
        } catch (SQLException e) {
            throw new WebApplicationException(e, Response.Status.INTERNAL_SERVER_ERROR);
        }

        return Response.ok().build();
    }

    @Override
    public Response getAllVersionsOfProject(String tenantDomain, int projectId) {

        Response response = null;

        try {
            List<Version> versions =
                                     DAODelegate.getVersionInstance()
                                                .getVersionListOfProjectByProjectId(projectId);
            GenericEntity<List<Version>> entity = new GenericEntity<List<Version>>(versions) {
            };
            response = Response.ok().entity(entity).build();
        } catch (IssueTrackerException e) {
            throw new WebApplicationException(e, Response.Status.INTERNAL_SERVER_ERROR);
        } catch (SQLException e) {
            throw new WebApplicationException(e, Response.Status.INTERNAL_SERVER_ERROR);
        }

        return response;

    }

    @Override
    public Response getAllIssuesOfProject(String tenantDomain, int projectId) {
        
        Response response = null;

        
        return response; 
    }

    @Override
    public Response addNewIssueToProject(String tenantDomain, int projectId, Issue issue) {
        return null; 
    }
}
