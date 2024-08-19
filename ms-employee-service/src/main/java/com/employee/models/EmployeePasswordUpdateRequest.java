package com.employee.models;

public class EmployeePasswordUpdateRequest {
	String id;
	String currentPassword;
	String newPassword;
	
	public EmployeePasswordUpdateRequest(String id, String currentPassword, String newPassword) {
		super();
		this.id = id;
		this.currentPassword = currentPassword;
		this.newPassword = newPassword;
	}
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getCurrentPassword() {
		return currentPassword;
	}
	public void setCurrentPassword(String currentPassword) {
		this.currentPassword = currentPassword;
	}
	public String getNewPassword() {
		return newPassword;
	}
	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}
	
	
}
