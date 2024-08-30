package com.skillstorm.services;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.skillstorm.models.Task;

import sailpoint.plugin.PluginBaseHelper;
import sailpoint.plugin.PluginContext;
import sailpoint.tools.GeneralException;
import sailpoint.tools.IOUtil;

public class TaskService 
{
    
    private PluginContext context;
    private static int crawl = 0;  //crawls sql query forward by this much from last time (increments by 1 each time)
    

    public TaskService(PluginContext context)
    {
        this.context = context;
    }


    public void createTask(String taskName) throws GeneralException
    {
        Connection connection = null;
		PreparedStatement statement = null;
		
		try 
        {
			connection = context.getConnection();
			statement = PluginBaseHelper.prepareStatement(connection, "INSERT INTO ep_plugin_task_auto (task_name) VALUES (?)", taskName);
			
			statement.executeUpdate();
			
			System.out.println("Updated task " + taskName);
			
		} 
        catch(SQLException e) 
        {
			throw new GeneralException(e);
		} 
        finally 
        {
			IOUtil.closeQuietly(statement);
			IOUtil.closeQuietly(connection);
		}
    }


    public List<Task> getAllTasks() throws GeneralException
    {
        Connection connection = null;
		PreparedStatement statement = null;
		
		try 
        {
			connection = context.getConnection();
			statement = PluginBaseHelper.prepareStatement(connection, "SELECT * FROM  ep_plugin_task_auto");
			
			ResultSet result = statement.executeQuery();
			
            List<Task> tasks = new ArrayList<>();
            
            while(result.next())
            {
                tasks.add(new Task(result.getInt("id"), result.getString("task_name")));
            }

            System.out.println("Got " + tasks.size() + " tasks");
            return tasks;
						
		} 
        catch(SQLException e) 
        {
			throw new GeneralException(e);
		} 
        finally 
        {
			IOUtil.closeQuietly(statement);
			IOUtil.closeQuietly(connection);
		}
    }

    //gets an individual task based on the id variable in this class
    public Task getTask() throws GeneralException
    {
        Connection connection = null;
		PreparedStatement statement = null;
		
		try 
        {
			connection = context.getConnection();
            
            
            Task task = null;

            
            statement = PluginBaseHelper.prepareStatement(connection, "SELECT COUNT(id) FROM ep_plugin_task_auto");
            ResultSet result = statement.executeQuery();

            if(result.next());
            {
                int count = result.getInt("COUNT(id)");
            
                if (count == 0)
                {
                    return task;
                }

                if (crawl >= count )
                {
                    crawl = 0;
                }   
                
            }


            statement = PluginBaseHelper.prepareStatement(connection, "SELECT * FROM ep_plugin_task_auto LIMIT 1 OFFSET ?", crawl);
            
            result = statement.executeQuery();
            

            if(result.next())
            {
                task = new Task(result.getInt("id"), result.getString("task_name"));

            }


        
            System.out.println("Got task " + task.getTaskName());
            crawl++;

            
            return task;
						
		} 
        catch(SQLException e) 
        {
			throw new GeneralException(e);
		} 
        finally 
        {
			IOUtil.closeQuietly(statement);
			IOUtil.closeQuietly(connection);
		}
    }

    public void deleteTask(int id) throws GeneralException
    {
        Connection connection = null;
		PreparedStatement statement = null;
		
		try 
        {
			connection = context.getConnection();
			statement = PluginBaseHelper.prepareStatement(connection, "DELETE FROM  ep_plugin_task_auto WHERE id = ?", id);
			
			statement.executeUpdate();
			System.out.println("Task deleted");
						
		} 
        catch(SQLException e) 
        {
			throw new GeneralException(e);
		} 
        finally 
        {
			IOUtil.closeQuietly(statement);
			IOUtil.closeQuietly(connection);
		}
    }

}
