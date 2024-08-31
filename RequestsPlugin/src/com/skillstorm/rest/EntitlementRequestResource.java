package com.skillstorm.rest;

import com.skillstorm.model.EntitlementRequest;
import com.skillstorm.services.EntitlementRequestService;
import sailpoint.rest.plugin.AllowAll;
import sailpoint.rest.plugin.BasePluginResource;
import sailpoint.tools.GeneralException;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;


@Path("Request")
public class EntitlementRequestResource extends BasePluginResource
{

    @Override
    public String getPluginName()
    {
        return "Request Plugin";
    }

    private EntitlementRequestService service()
    {
        return new EntitlementRequestService(this);
    }

    @POST
    @Path("createRequest/{ownerId}/{requesterId}/{entitlementId}/{description}")
    @AllowAll
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public EntitlementRequest createRequest(@PathParam("ownerId") String ownerId, @PathParam("requesterId") String requesterId,@PathParam("entitlementId") String entitlementId, @PathParam("description") String description) throws GeneralException
    {
        return service().createRequest(ownerId, requesterId, entitlementId, description);
    }

    @GET
    @Path("getPending/{ownerId}/{processed}")
    @AllowAll
    @Produces(MediaType.APPLICATION_JSON)
    public List<EntitlementRequest> getRequestsByOwnerId(@PathParam("ownerId") String ownerId, @PathParam("processed") boolean processed) throws GeneralException
    {
        return service().getRequestByManagerAndStatus(ownerId, processed);
    }

    @GET
    @Path("getByOwner/{ownerId}")
    @AllowAll
    @Produces(MediaType.APPLICATION_JSON)
    public List<EntitlementRequest> getPendingRequestsByManager(@PathParam("ownerId") String ownerId) throws GeneralException
    {
        return service().getRequestsByOwnerId(ownerId);
    }

    @PUT
    @Path("processRequest/{requestId}/{approved}")
    @AllowAll
    @Consumes(MediaType.APPLICATION_JSON)
    public Response processRequest(@PathParam("requestId") int requestId, @PathParam("approved") boolean approved) throws GeneralException
    {
        service().processRequest(requestId, approved);
        return Response.ok().entity("Request processed successfully.").build();
    }

    @DELETE
    @Path("deleteRequest/{requestId}")
    @AllowAll
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteRequestById(@PathParam("requestId") int requestId) throws GeneralException
    {
        service().deleteRequestById(requestId);
        return Response.ok().entity("Request deleted successfully.").build();
    }

}
