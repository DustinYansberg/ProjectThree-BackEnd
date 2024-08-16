package com.employee.models;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * EmployeeResponse
 * An object used for returning data from the backend.
 * Contains the fields necessary for the backend to use.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class EmployeeResponse {
	@JsonProperty("id") String id;
	@JsonProperty("userName") String userName;
	@JsonProperty("name") Map<String, Object> name;
	@JsonProperty("displayName") String displayName;
	@JsonProperty("userType") String userType;
	@JsonProperty("active") boolean active;
	@JsonProperty("password") String password;
	@JsonProperty("emails") Map<String, Object>[] emails;
	
	@JsonProperty("employeeDetails") Map<String, Object> employeeDetails;
	@JsonProperty("urn:ietf:params:scim:schemas:sailpoint:1.0:User")
	private void setEmployeeDetailsFromJson(Map<String, Object> json) {
		employeeDetails = new HashMap<>();
		employeeDetails.put("accounts", json.get("accounts"));
		employeeDetails.put("entitlements", json.get("entitlements"));
		employeeDetails.put("roles", json.get("roles"));
		employeeDetails.put("isManager", json.get("isManager"));
		employeeDetails.put("Department", json.get("department"));
		employeeDetails.put("jobTitle", json.get("jobTitle"));
	}
	
	@JsonProperty("manager") Map<String, Object> manager;
	@SuppressWarnings("unchecked")
	@JsonProperty("urn:ietf:params:scim:schemas:extension:enterprise:2.0:User")
	private void setManagerFromJson(Map<String, Object> json) {
		this.manager = (Map<String, Object>) json.get("manager");
	}
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public Map<String, Object> getName() {
		return name;
	}
	public void setName(Map<String, Object> name) {
		this.name = name;
	}
	public String getDisplayName() {
		return displayName;
	}
	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}
	public String getUserType() {
		return userType;
	}
	public void setUserType(String userType) {
		this.userType = userType;
	}
	public boolean isActive() {
		return active;
	}
	public void setActive(boolean active) {
		this.active = active;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public Map<String, Object>[] getEmails() {
		return emails;
	}
	public void setEmails(Map<String, Object>[] emails) {
		this.emails = emails;
	}
	public Map<String, Object> getEmployeeDetails() {
		return employeeDetails;
	}
	public void setEmployeeDetails(Map<String, Object> employeeDetails) {
		this.employeeDetails = employeeDetails;
	}
	public Map<String, Object> getManager() {
		return manager;
	}
	public void setManager(Map<String, Object> manager) {
		this.manager = manager;
	}

	@Override
	public String toString() {
		return "EmployeeResponse [id=" + id + ", userName=" + userName + ", name=" + name + ", displayName="
				+ displayName + ", userType=" + userType + ", active=" + active + ", password=" + password + ", emails="
				+ Arrays.toString(emails) + ", employeeDetails=" + employeeDetails + ", manager=" + manager + "]";
	}
}