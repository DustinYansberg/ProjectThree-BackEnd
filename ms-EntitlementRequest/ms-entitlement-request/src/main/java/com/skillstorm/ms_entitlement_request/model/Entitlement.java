package com.skillstorm.ms_entitlement_request.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Entitlement
{
    private String id;
    private String displayableName;
    private String type;
    private String applicationDisplayName;

    public Entitlement()
    {
    }

    public Entitlement(String id, String applicationDisplayName, String type, String displayableName)
    {
        this.id = id;
        this.applicationDisplayName = applicationDisplayName;
        this.type = type;
        this.displayableName = displayableName;
    }



    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDisplayableName() {
        return displayableName;
    }

    public void setDisplayableName(String displayableName) {
        this.displayableName = displayableName;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getApplicationDisplayName() {
        return applicationDisplayName;
    }

    public void setApplicationDisplayName(String applicationDisplayName) {
        this.applicationDisplayName = applicationDisplayName;
    }
}
