package com.skillstorm.rest;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.skillstorm.models.LogEntry;
import com.skillstorm.services.LogEntryService;

import sailpoint.rest.plugin.AllowAll;
import sailpoint.rest.plugin.BasePluginResource;
import sailpoint.tools.GeneralException;

@Path("ep/logs")
public class LogEntryResource extends BasePluginResource {

    @Override
    public String getPluginName() {
	return "EmailPlugin";
    }

    private LogEntryService service() {
	return new LogEntryService(this);
    }

    @GET
    @Path("getAll")
    @AllowAll
    @Produces(MediaType.APPLICATION_JSON)
    public List<LogEntry> getAllLogs() throws GeneralException {
	return service().getAllLogs();
    }
}
