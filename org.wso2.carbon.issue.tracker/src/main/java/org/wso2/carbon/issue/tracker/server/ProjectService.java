package org.wso2.carbon.issue.tracker.server;

import org.wso2.carbon.issue.tracker.bean.Issue;
import org.wso2.carbon.issue.tracker.bean.Project;
import org.wso2.carbon.issue.tracker.bean.Version;

import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

/**
 *
 * Service class defines, operations related to Project related services
 *
 */
@Path("/{tenantDomain}/project")
public interface ProjectService {

    @GET
    @Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
    public Response getAllProject(@PathParam("tenantDomain") String tenantDomain);

    @GET
    @Path("/{projectId}")
    @Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
    public Response getProject(@PathParam("tenantDomain") String tenantDomain, @PathParam("projectId") int projectId);

    @POST
    @Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    @Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
    public Response addProject(@PathParam("tenantDomain") String tenantDomain, Project project);

    @POST
    @Path("/{projectId}")
    @Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    @Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
    public Response editProject(@PathParam("tenantDomain") String tenantDomain, @PathParam("projectId") int projectId, Project project);

    @GET
    @Path("/{projectId}/version")
    @Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
    public Response getAllVersionsOfProject(@PathParam("tenantDomain") String tenantDomain, @PathParam("projectId") int projectId);

    @GET
    @Path("/{projectId}/issue")
    @Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
    public Response getAllIssuesOfProject(@PathParam("tenantDomain") String tenantDomain, @PathParam("projectId") int projectId);

    @POST
    @Path("/{projectId}/issue")
    @Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    @Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
    public Response addNewIssueToProject(@PathParam("tenantDomain") String tenantDomain, @PathParam("projectId") int projectId, Issue issue);

    @POST
    @Path("/{projectId}/version")
    @Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    @Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
    public Response addNewVersionToProject(@PathParam("tenantDomain") String tenantDomain, @PathParam("projectId") int projectId, Version version);

}
