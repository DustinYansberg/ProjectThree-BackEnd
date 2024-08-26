package com.skillstorm.rest;

import java.util.List;
import java.util.Map;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.skillstorm.services.EmailTemplateService;

import sailpoint.rest.plugin.AllowAll;
import sailpoint.rest.plugin.BasePluginResource;
import sailpoint.tools.GeneralException;

@Path("ep/iiqemail")
public class EmailTemplateResource extends BasePluginResource {

    public EmailTemplateService service() {
	return new EmailTemplateService(this);
    }

    @Override
    public String getPluginName() {
	return "EmailPlugin";
    }

    @GET
    @Path("getAll")
    @AllowAll
    @Produces(MediaType.APPLICATION_JSON)
    public List<Map<String, String>> getAllEmailTemplates() throws GeneralException {
	return service().getAllEmailTemplates();
    }

    @GET
    @Path("get/{id}")
    @AllowAll
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Map<String, String> getAllEmailTemplateById(@PathParam("id") String id) throws GeneralException {
	return service().getEmailTemplateById(id);
    }

}
