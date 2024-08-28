package com.skillstorm.models;

import java.util.Date;

public class Notification {

    private int notificationId;
    private String identityId;
    private String applicationId;
    private String message;
    private boolean checked;
    private Date createdAt;

    public Notification() {

    }

    public Notification(String identityId, String applicationId) {
	this.identityId = identityId;
	this.applicationId = applicationId;
    }

    public Notification(String identityId, String applicationId, String message, boolean checked, Date createdAt) {
	this.identityId = identityId;
	this.applicationId = applicationId;
	this.message = message;
	this.checked = checked;
	this.createdAt = createdAt;
    }

    public Notification(int notificationId, String identityId, String applicationId, String message, boolean checked,
	    Date createdAt) {
	this.notificationId = notificationId;
	this.identityId = identityId;
	this.applicationId = applicationId;
	this.message = message;
	this.checked = checked;
	this.createdAt = createdAt;
    }

    public int getNotificationId() {
	return notificationId;
    }

    public void setNotificationId(int notificationId) {
	this.notificationId = notificationId;
    }

    public String getIdentityId() {
	return identityId;
    }

    public void setIdentityId(String identityId) {
	this.identityId = identityId;
    }

    public String getApplicationId() {
	return applicationId;
    }

    public void setApplicationId(String applicationId) {
	this.applicationId = applicationId;
    }

    public String getMessage() {
	return message;
    }

    public void setMessage(String message) {
	this.message = message;
    }

    public boolean isChecked() {
	return checked;
    }

    public void setChecked(boolean checked) {
	this.checked = checked;
    }

    public Date getCreatedAt() {
	return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
	this.createdAt = createdAt;
    }

}
