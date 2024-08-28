package com.skillstorm.rest;

import java.util.List;
import java.util.Map;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.skillstorm.models.Notification;
import com.skillstorm.services.NotificationService;

import sailpoint.rest.plugin.AllowAll;
import sailpoint.rest.plugin.BasePluginResource;
import sailpoint.tools.GeneralException;

// BASE URL FOR ALL PLUGIN ENDPOINTS: http://localhost:8080/identityiq/plugin/rest/

@Path("NotificationPlugin")
public class NotificationResource extends BasePluginResource {

	// this is a required override for the BasePluginResource interface
	@Override
	public String getPluginName() {
		return "NotificationPlugin";
	}
	
	private NotificationService service() {
		return new NotificationService(this);
	}
	
	@GET
	@Path("test")
	@AllowAll
	@Produces(MediaType.APPLICATION_JSON)
	public String test() throws GeneralException {
		return "Hello";
	}
	
	@GET
	@Path("notifications")
	@AllowAll
	@Produces(MediaType.APPLICATION_JSON)
	public List<Notification> getAllNotifications() throws GeneralException {
		return service().getAllNotifications();
	}
	
	@POST
	@Path("notification")
	@AllowAll
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Notification createNotification(Map<String, String> body) throws GeneralException {
		String identityId = body.get("identityId");
		String applicationId = body.get("applicationId");
		return service().createNotification(identityId, applicationId);
	}
	
	@GET
	@Path("notifications/{identityId}")
	@AllowAll
	@Produces(MediaType.APPLICATION_JSON)
	public List<Notification> getNotificationById(@PathParam("identityId") String identityId) throws GeneralException {
		return service().getNotificationsByIdentityId(identityId);
	}
	
	@PUT
	@Path("/notifications/check/{identityId}")
	@AllowAll
	public int readAll(@PathParam("identityId") String identityId) throws GeneralException {
		return service().readAllNotificationsByIdentityId(identityId);
	}
}










