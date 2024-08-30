package com.skillstorm.models;

public class Task 
{
    
    private int id;
    private String taskName;


    //constructor for objects coming FROM database
    public Task(int id, String taskName)
    {
        this.id = id;
        this.taskName = taskName;
    }

    //constructor for objects going TO database
    public Task(String taskName)
    {
        this.taskName = taskName;
    }

    //getter and setter methods
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    

}
