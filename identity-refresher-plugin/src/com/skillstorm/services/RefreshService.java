package com.skillstorm.services;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
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
   
    
    private static int position = 0;        //tracks where we are 
    private static int quantity = 20;       //the number of records we want to refresh in database (20 default)
    private static int identityQty = 0; //this is set by our count query method
    private static int iterations = 0;      //counts the number of times we've executed. Resets every 5 iterations

    public RefreshService(PluginContext context)
    {
        this.context = context;
    }


    /**
     * The center of this plugin
     * Performs a SQL update against the identityiq database
     * marks X identities at a time for refresh
     * 
     * In context, the results of this task are then used by our automated service task
     * to refresh the identities in our database gradually
     * 
     */
    public void markForRefresh() throws GeneralException
    {
        if(iterations%5==0) //check if we've iterated enough
        {
            System.out.println("Since we have " + iterations + " iterations, we need to check our identity count");

            System.out.println("Identity count is currently " + identityQty);
            //put get count method here to check number of identities
            setIdentityQuantity();
            System.out.println("Now it's " + identityQty);
            //reset the count
            iterations = 0;
            System.out.println("Iterations reset to " + iterations);


        }

        if(position > identityQty)  //if we're beyond the end, then we reset our position to the start
        { 
            position = 0;
        }

        Connection connection = null;
        PreparedStatement databaseSelect = null;
        PreparedStatement updateStatement = null;     //this one is for our update query
        

        try 
        {
            System.out.println("Method start");
            connection = Environment.getEnvironment().getSpringDataSource().getConnection();
            System.out.println("Hello, world! Connection established!");
            databaseSelect = PluginBaseHelper.prepareStatement(connection, "USE identityiq;");
            System.out.println("Prepared the SQL statement to use the correct DB");
            databaseSelect.execute();
            System.out.println("selected the database we're using");

            updateStatement = PluginBaseHelper.prepareStatement(

                  connection
                , "UPDATE spt_identity JOIN (SELECT id FROM spt_identity LIMIT ? OFFSET ?) AS subquery ON spt_identity.id = subquery.id SET spt_identity.needs_refresh = 1;"
            
                
                , quantity, position
                );
            System.out.println("Prepared the update statement");
            updateStatement.execute();
            System.out.println("Query performed!");
            position += quantity;
            System.out.println("We are at position " + position);
            System.out.println("And we are printing " + quantity + " at a time!");
            iterations++;
            System.out.println("We've performed task markForRefresh() " + iterations + " times since checking the identity count!");

        }
        catch(SQLException e)
        {
            throw new GeneralException(e);
        }
        finally 
        {
            IOUtil.closeQuietly(databaseSelect);
            IOUtil.closeQuietly(updateStatement);
            IOUtil.closeQuietly(connection);
        }
    }

    /**
     * This method performs a SQL query to get the total number of identities in the database
     * Stored in the identityQty variable
     */
    public void setIdentityQuantity() throws GeneralException
    {
        Connection connection = null;
        PreparedStatement databaseSelect = null;
        PreparedStatement countStatement = null;

        try
        {
            System.out.println("Trying to get the number of identities");
            connection = Environment.getEnvironment().getSpringDataSource().getConnection();
            System.out.println("Connection established");
            databaseSelect = PluginBaseHelper.prepareStatement(connection, "USE identityiq;");
            System.out.println("Set our use database statement");
            databaseSelect.execute();
            System.out.println("Executed use database statement");
            countStatement = PluginBaseHelper.prepareStatement(connection, "SELECT COUNT(id) FROM spt_identity;");
            System.out.println("Prepared out count statement");
            ResultSet result = countStatement.executeQuery();
            System.out.println("Executed our query");

            if(result.next())
                identityQty = result.getInt("COUNT(id)");
            System.out.println("Got our quantity of identities: " + identityQty);

        }
        catch(SQLException e)
        {
            throw new GeneralException(e);
        }
        finally
        {
            IOUtil.closeQuietly(databaseSelect);
            IOUtil.closeQuietly(countStatement);
            IOUtil.closeQuietly(connection);
        }
    }
    


}
