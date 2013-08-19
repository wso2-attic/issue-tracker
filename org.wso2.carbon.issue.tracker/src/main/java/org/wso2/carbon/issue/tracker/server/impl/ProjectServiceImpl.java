package org.wso2.carbon.issue.tracker.server.impl;

import java.sql.SQLException;
import java.util.List;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.UriInfo;

import org.apache.cxf.common.util.StringUtils;
import org.wso2.carbon.issue.tracker.bean.Issue;
import org.wso2.carbon.issue.tracker.bean.Project;
import org.wso2.carbon.issue.tracker.bean.Version;
import org.wso2.carbon.issue.tracker.delegate.DAODelegate;
import org.wso2.carbon.issue.tracker.server.ProjectService;
import org.wso2.carbon.issue.tracker.util.IssueTrackerException;
import org.wso2.carbon.issue.tracker.util.TenantUtils;
import org.wso2.carbon.user.api.UserStoreException;

public class ProjectServiceImpl implements ProjectService {
    @Override
    public Response getAllProject(String tenantDomain) {

        Response response = null;

        try {

            int tenantId = TenantUtils.getTenantId(tenantDomain);

            List<Project> projects =
                                     DAODelegate.getProjectInstance()
                                                .getProjectsByOrganizationId(tenantId);

            GenericEntity<List<Project>> entity = new GenericEntity<List<Project>>(projects) {
            };
            response = Response.ok().entity(entity).build();

        } catch (SQLException e) {
            throw new WebApplicationException(e, Response.Status.INTERNAL_SERVER_ERROR);
        } catch (UserStoreException use) {
            throw new WebApplicationException(use, Response.Status.INTERNAL_SERVER_ERROR);
        }

        return response;
    }

    @Override
    public Response getProject(String tenantDomain, int projectId) {
        Response response = null;
        try {
            Project project = DAODelegate.getProjectInstance().get(projectId);

            if (project != null) {
                int tenantId = TenantUtils.getTenantId(tenantDomain);

                if (project.getOrganizationId() == tenantId) {
                    response = Response.ok().entity(project).build();
                } else {
                    response = Response.status(Status.NOT_FOUND).build();
                }
            } else {
                response = Response.status(Status.NOT_FOUND).build();
            }

        } catch (SQLException e) {
            throw new WebApplicationException(e, Response.Status.INTERNAL_SERVER_ERROR);
        } catch (UserStoreException use) {
            throw new WebApplicationException(use, Response.Status.INTERNAL_SERVER_ERROR);
        }
        return response;
    }

    @Override
    public Response addProject(String tenantDomain, Project project, @Context UriInfo ui) {

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
            int tenantId = TenantUtils.getTenantId(tenantDomain);
            project.setOrganizationId(tenantId);
            DAODelegate.getProjectInstance().add(project);
        } catch (SQLException e) {
            throw new WebApplicationException(e, Response.Status.INTERNAL_SERVER_ERROR);
        } catch (UserStoreException use) {
            throw new WebApplicationException(use, Response.Status.INTERNAL_SERVER_ERROR);
        }

        return Response.created(ui.getAbsolutePath()).build();
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

        Response response = null;

        try {
            
            //check weather the project exists before proceeding.
            project.setId(projectId);
            if (DAODelegate.getProjectInstance().update(project)) {
                response = Response.ok().build();
            } else {
                response = Response.notModified().build();
            }

        } catch (SQLException e) {
            throw new WebApplicationException(e, Response.Status.INTERNAL_SERVER_ERROR);
        }

        return response;
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
        Response response = Response.ok().build();
        return response;
    }

    @Override
    public Response addNewIssueToProject(String tenantDomain, int projectId, Issue issue) {
        return Response.ok().build();
    }
}
