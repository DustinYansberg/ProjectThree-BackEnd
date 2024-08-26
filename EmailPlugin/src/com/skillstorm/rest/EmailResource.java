package com.skillstorm.rest;

import java.util.List;
import java.util.Map;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.skillstorm.models.Email;
import com.skillstorm.services.EmailService;
import com.skillstorm.services.LogEntryService;

import sailpoint.rest.plugin.AllowAll;
import sailpoint.rest.plugin.BasePluginResource;
import sailpoint.tools.GeneralException;

@Path("ep/email")
public class EmailResource extends BasePluginResource {

    @Override
    public String getPluginName() {
	return "EmailPlugin";
    }

    private EmailService service() {
	return new EmailService(this);
    }

    private LogEntryService logService() {
	return new LogEntryService(this);
    }

    @GET
    @Path("getAll")
    @AllowAll
    @Produces(MediaType.APPLICATION_JSON)
    public List<Email> getAllEmails() throws GeneralException {
	return service().getAllEmails();
    }

    @GET
    @Path("get/{id}")
    @AllowAll
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Email getEmailById(@PathParam("id") int id) throws GeneralException {
	return service().getEmailById(id);
    }

    @POST
    @Path("saveTemplate")
    @AllowAll
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public int createEmail(Map<String, String> body) throws GeneralException {

	String name = body.get("name");
	String subject = body.get("subject");
	String emailBody = body.get("body");

	return service().createEmail(name, subject, emailBody);
    }

    @POST
    @Path("send")
    @AllowAll
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public int sendEmail(Map<String, String> body) throws GeneralException {

	String receiverId = body.get("receiverId");
	String emailId = body.get("emailId");

	return logService().addToLog(receiverId, emailId);
    }

}
