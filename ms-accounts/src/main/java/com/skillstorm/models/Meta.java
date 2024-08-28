package com.skillstorm.models;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Meta {

	@JsonProperty("created")
	private Date created;
	@JsonProperty("modified")
    private Date lastModified;
	
	
	
	public Meta() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Meta(Date created, Date lastModified) {
		super();
		this.created = created;
		this.lastModified = lastModified;
	}
	public Date getCreated() {
		return created;
	}
	public void setCreated(Date created) {
		this.created = created;
	}
	public Date getLastModified() {
		return lastModified;
	}
	public void setLastModified(Date lastModified) {
		this.lastModified = lastModified;
	}
	

    
}
