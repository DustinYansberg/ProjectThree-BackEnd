package com.document.models;

import java.util.Date;

public class Document {
	
	 private int id;
	    private String identityId;
	    private String name;
	    private String fileData;  
	    private boolean isCompleted;
	    private Date assignedDateTime;  

	    public Document(String identityId, String name, String fileData, boolean isCompleted, Date assignedDateTime) {
	        this.setIdentityId(identityId);
	    	this.name = name;
	        this.fileData = fileData;
	        this.isCompleted = false; 
	        this.assignedDateTime = assignedDateTime; 
	    }

	    public int getId() {
	        return id;
	    }

	    public void setId(int id) {
	        this.id = id;
	    }

	    public String getName() {
	        return name;
	    }

	    public void setName(String name) {
	        this.name = name;
	    }

	    public String getFileData() {
	        return fileData;
	    }

	    public void setFileData(String fileData) {
	        this.fileData = fileData;
	    }

	    public boolean isCompleted() {
	        return isCompleted;
	    }

	    public void setCompleted(boolean isCompleted) {
	        this.isCompleted = isCompleted;
	    }

	    public Date getAssignedDateTime() {
	        return assignedDateTime;
	    }

	    public void setAssignedDateTime(Date assignedDateTime) {
	        this.assignedDateTime = assignedDateTime;
	    }

		public String getIdentityId() {
			return identityId;
		}

		public void setIdentityId(String identityId2) {
			this.identityId = identityId2;
		}
	}