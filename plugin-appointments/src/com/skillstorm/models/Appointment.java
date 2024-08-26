package com.skillstorm.models;

public class Appointment {

	private int id;
	private String title;
	private String description;
	private String datetime;
	private String ownerId;
	private String attendeeId;
	
	public Appointment() {
		
	}
	
	

	public Appointment(int id, String title, String description, String datetime, String ownerId, String attendeeId) {
		super();
		this.id = id;
		this.title = title;
		this.description = description;
		this.datetime = datetime;
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



	public String getTime() {
		return datetime;
	}

	public void setTime(String datetime) {
		this.datetime = datetime;
	}
}
