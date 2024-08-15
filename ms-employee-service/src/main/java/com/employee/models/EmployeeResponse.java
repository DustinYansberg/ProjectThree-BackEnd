package com.employee.models;

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
		employeeDetails.put("isManager", json.get("isManager"));
		employeeDetails.put("department", json.get("department"));
		employeeDetails.put("jobTitle", json.get("jobTitle"));
	}
	
	@JsonProperty("manager") Map<String, Object> manager;
	@SuppressWarnings("unchecked")
	@JsonProperty("urn:ietf:params:scim:schemas:extension:enterprise:2.0:User")
	private void setManagerFromJson(Map<String, Object> json) {
		this.manager = (Map<String, Object>) json.get("manager");
	}
}

class Name {
	@JsonProperty("formatted") String formattedName;
	@JsonProperty("familyName") String familyName;
	@JsonProperty("givenName") String givenName;
	public Name(String formattedName, String familyName, String givenName) {
		super();
		this.formattedName = formattedName;
		this.familyName = familyName;
		this.givenName = givenName;
	}
	
}

class Email {
	@JsonProperty("type") String type;
	@JsonProperty("value") String value;
	@JsonProperty("primary") boolean primary;
	public Email(String type, String value, boolean primary) {
		super();
		this.type = type;
		this.value = value;
		this.primary = primary;
	}
}

class EmployeeDetails {
}

class Manager {
	@JsonProperty("displayName") String displayName;
	@JsonProperty("value") String value;
	@JsonProperty("$ref") String $ref;
	public Manager(String displayName, String value, String $ref) {
		super();
		this.displayName = displayName;
		this.value = value;
		this.$ref = $ref;
	}
}