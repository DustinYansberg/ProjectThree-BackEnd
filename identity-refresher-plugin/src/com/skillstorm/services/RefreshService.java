package com.skillstorm.services;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import sailpoint.plugin.PluginBaseHelper;
import sailpoint.plugin.PluginContext;
import sailpoint.server.Environment;
import sailpoint.tools.GeneralException;
import sailpoint.tools.IOUtil;

/**
 * This class queries the database to update identities
 * 
 */
public class RefreshService 
{
    
    private PluginContext context;      //context injected from our automated service
   
    
    private static int position = 0;    //tracks where we are 
    private static int quantity = 20;   //the number of records we want to refresh in database (20 default)

    public RefreshService(PluginContext context)
    {
        this.context = context;
    }


    public void markForRefresh() throws GeneralException
    {
        Connection connection = null;
        PreparedStatement sqlUse = null;
        PreparedStatement sqlUpdate = null;     //this one is for our update query
        

        try 
        {
            System.out.println("Method start");
            connection = Environment.getEnvironment().getSpringDataSource().getConnection();
            System.out.println("Hello, world! Connection established!");
            sqlUse = PluginBaseHelper.prepareStatement(connection, "USE identityiq;");
            System.out.println("Prepared the SQL statement to use the correct DB");
            sqlUse.execute();
            System.out.println("selected the database we're using");

            sqlUpdate = PluginBaseHelper.prepareStatement(

                  connection
                , "UPDATE spt_identity JOIN (SELECT id FROM spt_identity LIMIT ? OFFSET ?) AS subquery ON spt_identity.id = subquery.id SET spt_identity.needs_refresh = 1;"
            
                
                , quantity, position
                );
            System.out.println("Prepared the update statement");
            sqlUpdate.execute();
            System.out.println("Query performed!");
            position += quantity;
            System.out.println("We are at position " + position);
            System.out.println("And we are printing " + quantity + " at a time!");

        }
        catch(SQLException e)
        {
            throw new GeneralException(e);
        }
        finally 
        {
            IOUtil.closeQuietly(sqlUpdate);
            IOUtil.closeQuietly(connection);
        }
    }

    


}
