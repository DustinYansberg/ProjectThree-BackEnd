package com.skillstorm.server;

import java.util.HashMap;

import com.skillstorm.services.RefreshService;

import sailpoint.api.SailPointContext;
import sailpoint.api.TaskManager;
import sailpoint.server.BasePluginService;
import sailpoint.tools.GeneralException;

/**
 * This is the class that actually automates the database queries
 * 
 * 
 */
public class RefreshAutomatedService extends BasePluginService
{
    
    private RefreshService service;

    public RefreshAutomatedService()
    {
        service = new RefreshService(this);
    }



    //this section takes in SailPoint context and executes a task named "Refresh marked identities"
    @Override
    public void execute(SailPointContext context) throws GeneralException
    {
        
        //call to service method (injected this in constructor as context)
        service.markForRefresh();
        TaskManager tm = new TaskManager(context);
		//the below returns a result, so if we ever want to do something with it, we can
        tm.runSync("Refresh marked identities", new HashMap<>());
    }


    @Override
    public String getPluginName() 
    {
        return "IdentityRefresherPlugin";
    }

}

