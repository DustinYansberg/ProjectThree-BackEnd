package com.skillstorm.models;

import java.sql.Date;
import java.sql.Time;

public class LogEntry {
    private String receiverId;
    private int emailId;
    private Date createdAtDate;
    private Time createdAtTime;

    public LogEntry() {
	super();
    }

    public LogEntry(String receiverId, int emailId, Date createdAtDate, Time createdAtTime) {
	super();
	this.receiverId = receiverId;
	this.emailId = emailId;
	this.createdAtDate = createdAtDate;
	this.createdAtTime = createdAtTime;
    }

    public String getReceiverId() {
	return receiverId;
    }

    public void setReceiverId(String receiverId) {
	this.receiverId = receiverId;
    }

    public int getEmailId() {
	return emailId;
    }

    public void setEmailId(int emailId) {
	this.emailId = emailId;
    }

    public Date getCreatedAtDate() {
	return createdAtDate;
    }

    public void setCreatedAtDate(Date createdAtDate) {
	this.createdAtDate = createdAtDate;
    }

    public Time getCreatedAtTime() {
	return createdAtTime;
    }

    public void setCreatedAtTime(Time createdAtTime) {
	this.createdAtTime = createdAtTime;
    }

    @Override
    public String toString() {
	return "LogEntry [receiverId=" + receiverId + ", emailId=" + emailId + ", createdAtDate=" + createdAtDate
		+ ", createdAtTime=" + createdAtTime + "]";
    }

}
