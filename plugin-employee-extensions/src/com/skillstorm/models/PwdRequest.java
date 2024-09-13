package com.skillstorm.models;

public class PwdRequest {
	String id;
	String oldPwd;
	String newPwd;
	public PwdRequest(String id, String oldPwd, String newPwd) {
		super();
		this.id = id;
		this.oldPwd = oldPwd;
		this.newPwd = newPwd;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getOldPwd() {
		return oldPwd;
	}
	public void setOldPwd(String oldPwd) {
		this.oldPwd = oldPwd;
	}
	public String getNewPwd() {
		return newPwd;
	}
	public void setNewPwd(String newPwd) {
		this.newPwd = newPwd;
	}
}
