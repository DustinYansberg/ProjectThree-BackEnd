package com.skillstorm.models;

public class Appointment {

	private int id;
	private String title;
	private String description;
	private String datetime;
	private String organizerId;
	private String attendeeId;
	private boolean checkedIn;
	
	public Appointment() {
		
	}
	
	

	public Appointment(int id, String title, String description, String datetime, String organizerId, String attendeeId, boolean checkedIn) {
		super();
		this.id = id;
		this.title = title;
		this.description = description;
		this.datetime = datetime;
		this.organizerId = organizerId;
		this.attendeeId = attendeeId;
		this.checkedIn = checkedIn;
	}


	
	

	public String getDatetime() {
		return datetime;
	}



	public void setDatetime(String datetime) {
		this.datetime = datetime;
	}



	public boolean isCheckedIn() {
		return checkedIn;
	}



	public void setCheckedIn(boolean checkedIn) {
		this.checkedIn = checkedIn;
	}



	public String getOrganizerId() {
		return organizerId;
	}



	public void setOrganizerId(String organizerId) {
		this.organizerId = organizerId;
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
}
