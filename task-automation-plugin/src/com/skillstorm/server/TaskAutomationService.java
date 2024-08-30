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
        Task task = service.getTask();
        
        if(task!=null){
            TaskManager tm = new TaskManager(context);
            tm.runSync(task.getTaskName(), new HashMap<>());
        }
    }

    
}
