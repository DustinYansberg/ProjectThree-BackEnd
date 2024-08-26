package com.skillstorm.models;

import java.sql.Timestamp;

public class Appointment {

	private int id;
	private String title;
	private String description;
	private Timestamp time;
	private String ownerId;
	private String attendeeId;
	
	public Appointment() {
		
	}
	
	

	public Appointment(int id, String title, String description, Timestamp time, String ownerId, String attendeeId) {
		super();
		this.id = id;
		this.title = title;
		this.description = description;
		this.time = time;
		this.ownerId = ownerId;
		this.attendeeId = attendeeId;
	}


	

	public String getOwnerId() {
		return ownerId;
	}



	public void setOwnerId(String ownerId) {
		this.ownerId = ownerId;
	}



	public String getAttendeeId() {
		return attendeeId;
	}



	public void setAttendeeId(String attendeeId) {
		this.attendeeId = attendeeId;
	}



	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	

	public String getDescription() {
		return description;
	}



	public void setDescription(String description) {
		this.description = description;
	}



	public Timestamp getTime() {
		return time;
	}

	public void setTime(Timestamp time) {
		this.time = time;
	}
}
