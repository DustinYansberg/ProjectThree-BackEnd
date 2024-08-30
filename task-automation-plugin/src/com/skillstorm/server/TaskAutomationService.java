package com.skillstorm.server;

import java.util.HashMap;

import com.skillstorm.models.Task;
import com.skillstorm.services.TaskService;

import sailpoint.api.SailPointContext;
import sailpoint.api.TaskManager;
import sailpoint.server.BasePluginService;
import sailpoint.tools.GeneralException;

public class TaskAutomationService extends BasePluginService
{

    private TaskService service;

    public TaskAutomationService()
    {
        service = new TaskService(this);
    }


    @Override
    public String getPluginName() 
    {
        return "TaskAutomationPlugin";
    }


    @Override
    public void execute(SailPointContext context) throws GeneralException
    {
        //needs to have a try/catch here. We want to handle an exception from a non-existent Task
        Task task = service.getTask();
        
        try
        {
            
            
            if(task!=null)
            {

                TaskManager tm = new TaskManager(context);
                System.out.println("Executing task manager runSync");
                tm.runSync(task.getTaskName(), new HashMap<>());
                System.out.println("Executed successfully");
            }
        } 
        catch (GeneralException e)
        {
            System.out.println("Couldn't execute that task. Deleting that record.");
            if(task!=null)
                service.deleteTask(task.getId());
        }
    }

    
}
