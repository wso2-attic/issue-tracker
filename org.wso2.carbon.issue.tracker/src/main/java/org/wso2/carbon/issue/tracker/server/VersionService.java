package org.wso2.carbon.issue.tracker.server;

import org.wso2.carbon.issue.tracker.bean.Version;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/versionService")
public interface VersionService {

    @GET
    @Path("/project/{project_id}/")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Response getVersionListOfProject(@PathParam("project_id") int projectId);

    @POST
    @Path("/postVersion")
    @Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Response addVersionToProject(Version version);

    @GET
    @Path("/helloVersion")
    public String sayHello();

}
