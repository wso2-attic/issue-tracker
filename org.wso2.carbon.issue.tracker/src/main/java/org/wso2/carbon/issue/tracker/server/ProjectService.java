package org.wso2.carbon.issue.tracker.server;

import org.wso2.carbon.issue.tracker.bean.Comment;
import org.wso2.carbon.issue.tracker.bean.Issue;
import org.wso2.carbon.issue.tracker.bean.Project;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;


@Path("/project")
public interface ProjectService {

    @GET
    @Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
    public Response getAllProject();

    @GET
    @Path("/{projectId}")
    @Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
    public Response getProject(@PathParam("projectId") int projectId);

    @POST
    @Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    @Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
    public Response addProject(Project project);

    @POST
    @Path("/{projectId}")
    @Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    @Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
    public Response editProject(@PathParam("projectId") int projectId, Project project);

    @GET
    @Path("/{projectId}/version")
    @Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
    public Response getAllVersionsOfProject(@PathParam("projectId") int projectId);

    @GET
    @Path("/{projectId}/issue")
    @Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
    public Response getAllIssuesOfProject(@PathParam("projectId") int projectId);

    @POST
    @Path("/{projectId}/issue")
    @Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    @Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
    public Response addNewIssueToProject(@PathParam("projectId") int projectId, Issue issue);

}
