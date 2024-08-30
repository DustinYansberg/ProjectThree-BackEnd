package com.skillstorm.rest;

import java.util.List;
import java.util.Map;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.skillstorm.models.Task;
import com.skillstorm.services.TaskService;

import sailpoint.rest.plugin.AllowAll;
import sailpoint.rest.plugin.BasePluginResource;
import sailpoint.tools.GeneralException;

@Path("AutomatedTasks")
public class TaskResource extends BasePluginResource
{

    //method to get service and inject this as context
    private TaskService service()
    {
        return new TaskService(this);
    }

    //Required by BasePluginResource
    @Override
    public String getPluginName() 
    {
        return "TaskAutomationPlugin";
    }



    @POST
    @Path("add")
    @AllowAll
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public void createTask(Map<String, String> body) throws GeneralException
    {
        service().createTask(body.get("taskName"));
    }

    @GET
    @Path("all")
    @AllowAll
    @Produces(MediaType.APPLICATION_JSON)
    public List<Task> getAllTasks() throws GeneralException
    {
        return service().getAllTasks();
    }

    @DELETE
    @Path("delete/{id}")
    @AllowAll
    public void deleteTask(@PathParam("id") int id) throws GeneralException
    {
        service().deleteTask(id);
    }
    
}
