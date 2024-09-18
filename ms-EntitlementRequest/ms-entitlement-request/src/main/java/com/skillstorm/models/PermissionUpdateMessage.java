package com.skillstorm.models;

import java.io.Serial;
import java.io.Serializable;

public class PermissionUpdateMessage implements Serializable
{
    private String id;
    private String permission;
    @Serial
    private static final long serialVersionUID = 1L;

    public PermissionUpdateMessage(String id, String permission)
    {
        this.id = id;
        this.permission = permission;
    }

    public String getId()
    {
        return id;
    }

    public void setId(String id)
    {
        this.id = id;
    }

    public String getPermission()
    {
        return permission;
    }

    public void setPermission(String permission)
    {
        this.permission = permission;
    }

    @Override
    public String toString()
    {
        return "PermissionUpdateMessage{" +
                "id='" + id + '\'' +
                ", permission='" + permission + '\'' +
                '}';
    }
}
