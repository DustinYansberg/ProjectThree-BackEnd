package com.skillstorm.models;

public class LogEntry {
    private String receiverId;
    private int emailId;

    public LogEntry() {
	super();
    }

    public LogEntry(String receiverId, int emailId) {
	super();
	this.receiverId = receiverId;
	this.emailId = emailId;
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

}
