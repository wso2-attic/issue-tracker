package org.wso2.carbon.issue.tracker.server.impl;

import java.sql.SQLException;
import java.util.List;

import javax.ws.rs.PathParam;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.*;
import javax.ws.rs.core.Response.Status;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.cxf.common.util.StringUtils;
import org.wso2.carbon.issue.tracker.bean.Issue;
import org.wso2.carbon.issue.tracker.bean.Project;
import org.wso2.carbon.issue.tracker.bean.Version;
import org.wso2.carbon.issue.tracker.dao.CommentDAO;
import org.wso2.carbon.issue.tracker.dao.IssueDAO;
import org.wso2.carbon.issue.tracker.dao.VersionDAO;
import org.wso2.carbon.issue.tracker.delegate.DAODelegate;
import org.wso2.carbon.issue.tracker.server.ProjectService;
import org.wso2.carbon.issue.tracker.util.IssueTrackerException;
import org.wso2.carbon.issue.tracker.util.TenantUtils;
import org.wso2.carbon.user.api.UserStoreException;

public class ProjectServiceImpl implements ProjectService {
    private static final Log log = LogFactory.getLog(ProjectServiceImpl.class);

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
        Response response = null;
        try {
            List<Issue> issues = DAODelegate.getIssueInstance().getAllIssuesOfProject(projectId);
            GenericEntity<List<Issue>> entity = new GenericEntity<List<Issue>>(issues) {};

            response = Response.ok().entity(entity).build();
        } catch (SQLException e) {
            throw new WebApplicationException(e, Response.Status.INTERNAL_SERVER_ERROR);
        }

        return response;
    }

    @Override
    public Response addNewIssueToProject(String tenantDomain, int projectId, Issue issue) {
        if (log.isDebugEnabled()) {
            log.debug("Executing addNewIssueToProject, created by: " + issue.getOwner());
        }
        if(issue.getProjectId()<=0 || StringUtils.isEmpty(issue.getKey())){
            return Response.status(Response.Status.BAD_REQUEST).entity("Project ID or Project Key cannot be empty!").build();
        }

        if (StringUtils.isEmpty(issue.getSummary())) {
            return Response.status(Response.Status.BAD_REQUEST).entity("Issue summary cannot be empty!").build();
        }

        if (StringUtils.isEmpty(issue.getOwner())) {
            return Response.status(Response.Status.BAD_REQUEST).entity("Issue owner cannot be empty!").build();
        }

        if(StringUtils.isEmpty(issue.getType())){
            return Response.status(Response.Status.BAD_REQUEST).entity("Issue Type cannot be empty!").build();
        }

        if(StringUtils.isEmpty(issue.getPriority())){
            issue.setPriority("NORMAL");
        }

        if(StringUtils.isEmpty(issue.getStatus())){
            issue.setStatus("OPEN");
        }

        if(StringUtils.isEmpty(issue.getType())){
            return Response.status(Response.Status.BAD_REQUEST).entity("Issue Type cannot be empty!").build();
        }

        if(StringUtils.isEmpty(issue.getType())){
            return Response.status(Response.Status.BAD_REQUEST).entity("Issue Type cannot be empty!").build();
        }

        IssueDAO issueDAO = DAODelegate.getIssueInstance();
        try {
            boolean isInserted = issueDAO.add(issue);
            if (isInserted)
                return Response.ok(isInserted).type(MediaType.APPLICATION_JSON).build();
            else
                return Response.notModified().type(MediaType.APPLICATION_JSON_TYPE).entity("Issue is not successfully inserted.").build();
        } catch (SQLException e) {
            String msg = "Error while add Issue to Project";
            log.error(msg, e);
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).type(MediaType.APPLICATION_JSON_TYPE).build();
        }
    }

    @Override
    public Response addNewVersionToProject(String tenantDomain, int projectId, Version version) {
        if (log.isDebugEnabled()) {
            log.debug("Executing addNewVersionToProject, project versoin: " + version.getProjectVersion());
        }
        if(projectId <= 0 ){
            return Response.status(Response.Status.BAD_REQUEST).entity("Invalid Project!").build();
        }

        if (StringUtils.isEmpty(version.getProjectVersion())) {
            return Response.status(Response.Status.BAD_REQUEST).entity("Project Version cannot be empty!").build();
        }

        version.setProjectId(projectId);

        VersionDAO versionDAO = DAODelegate.getVersionInstance();
        try {
            boolean isInserted = versionDAO.addVersionForProject(version);
            if (isInserted)
                return Response.ok(isInserted).type(MediaType.APPLICATION_JSON).build();
            else
                return Response.notModified().type(MediaType.APPLICATION_JSON_TYPE).entity("Version is not successfully inserted.").build();
        } catch (SQLException e) {
            String msg = "Error while add Version to Project";
            log.error(msg, e);
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).type(MediaType.APPLICATION_JSON_TYPE).build();
        }
    }
}
