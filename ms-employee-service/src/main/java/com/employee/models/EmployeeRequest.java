package com.employee.models;

import org.springframework.beans.factory.annotation.Value;

/**
 * EmployeeRequest
 * A model used for transferring data to the backend from the frontend
 * in a form compatible with SCIM requests.
 * Contains only the fields that are going to be required by a frontend request.
 */
public class EmployeeRequest {
	//	The base URL of our SailPoint Employee database.
	@Value("${spring.datasource.url}/Users") private static String baseUrl;
	
	//	Variables for details of SailPoint identities.
	String userName;		//	Immutable, but required.
	String password;
	String firstName;
	String lastName;
	String email;
	String managerId;
	String softwareVersion;
	String administratorId;
	String displayName;
	boolean active;			//	If the user is an administrator or not. May not need this.
	String userType;		//	Required for POST and PUT.
	String department;
	
	/**
	 * Base constructor function for EmployeeRequest.
	 * @param userName
	 * @param password
	 * @param firstName
	 * @param lastName
	 * @param email
	 * @param managerId
	 * @param softwareVersion
	 * @param administratorId
	 * @param displayName
	 * @param active
	 * @param userType
	 * @param department
	 */
	public EmployeeRequest(String userName, String password, String firstName, String lastName, String email,
			/*String manager,*/ String managerId, String softwareVersion, /*String administrator,*/ String administratorId,
			String displayName, boolean active, String userType, String department) {
		super();
		this.userName = userName;
		this.password = password;
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.managerId = managerId;
		this.softwareVersion = softwareVersion;
		this.administratorId = administratorId;
		this.displayName = displayName;
		this.active = active;
		this.userType = userType;
		this.department = department; 
	}
	
	/**
	 * Converter constructor function for EmployeeRequest given an EmployeeResponse input object.
	 * Does not include softwareVersion or administratorId.
	 * @param resp - The EmployeeResponse object to convert to an EmployeeRequest object.
	 */
	public EmployeeRequest(EmployeeResponse resp) {
		super();
		this.userName = resp.userName;
		this.password = resp.password;
		this.firstName = resp.name.get("givenName").toString();
		this.lastName = resp.name.get("familyName").toString();
		this.email = resp.emails[0].get("value").toString();
		this.managerId = resp.manager.get("value") == null ? null : resp.manager.get("value").toString();
		this.displayName = resp.displayName;
		this.active = resp.active;
		this.userType = resp.userType;
		this.department = resp.employeeDetails.get("Department") == null ? null : resp.employeeDetails.get("Department").toString();
	}
	
	/**
	 * updateFields()
	 * A function that prepares this EmployeeRequest for a PUT call to SCIM API
	 * by updating its fields with new values from an EmployeeRequest.
	 * Does not update values that are null in newFields.
	 * @param newFields
	 */
	public void updateFields(EmployeeRequest newFields) {
		this.password = newFields.password == null ? this.password : newFields.password;
		this.firstName = newFields.firstName == null ? this.firstName : newFields.firstName;
		this.lastName = newFields.lastName == null ? this.lastName : newFields.lastName;
		this.email = newFields.email == null ? this.email : newFields.email;
		this.managerId = newFields.managerId == null ? this.managerId : newFields.managerId;
		this.softwareVersion = newFields.softwareVersion == null ? this.softwareVersion : newFields.softwareVersion;
		this.administratorId = newFields.administratorId == null ? this.administratorId : newFields.administratorId;
		this.displayName = newFields.displayName == null ? this.displayName : newFields.displayName;
		this.active = newFields.active;
		this.userType = newFields.userType == null ? this.userType : newFields.userType;
		this.department = newFields.department == null ? this.department : newFields.department;
	}
	
	/**
	 * toJsonString()
	 * Converts this object into a string that can be passed as a valid request body for a request to SCIM API.
	 * @return JSON string containing Employee request details, formatted for SCIM requests
	 */
	public String toJsonString() {
		String asJson = "{\r\n"
				+ "  \"userName\": \"" + userName + "\",\r\n"
				+ "  \"name\": {\r\n"
				+ "    \"formatted\": \"" + firstName + " " + lastName + "\",\r\n"
				+ "    \"familyName\": \"" + lastName + "\",\r\n"
				+ "    \"givenName\": \"" + firstName + "\"\r\n"
				+ "  },\r\n"
				+ "  \"displayName\": \"" + displayName + "\",\r\n"
				+ "  \"userType\": \"" + userType + "\",\r\n"
				+ "  \"active\": " + active + ",\r\n"
				+ "  \"password\": " + password + ",\r\n"
				+ "  \"emails\": [\r\n"
				+ "    {\r\n"
				+ "      \"type\": \"default\",\r\n"
				+ "      \"value\": \"" + email + "\",\r\n"
				+ "      \"primary\": \"true\"\r\n"
				+ "    }\r\n"
				+ "  ],\r\n"
				+ "  \"urn:ietf:params:scim:schemas:sailpoint:1.0:User\": {\r\n";

		if(administratorId != null)
			asJson = asJson
				+ "    \"administrator\": {\r\n"
				+ "      \"value\": \"" + administratorId + "\",\r\n"
				+ "      \"$ref\": \"" + baseUrl + "/" + administratorId + "\"\r\n" 
				+ "    },\r\n";
		asJson = asJson
				+ "    \"softwareVersion\": \"" + softwareVersion + "\",\r\n"
				+ "    \"Department\": \"" + department + "\"\r\n"
				+ "  },\r\n";
		if(managerId != null)
			asJson = asJson
				+ "  \"urn:ietf:params:scim:schemas:extension:enterprise:2.0:User\": {\r\n"
				+ "    \"manager\": {\r\n"
				+ "      \"value\": \"" + managerId + "\"\r\n"
				+ "    }\r\n"
				+ "  },\r\n";
		asJson = asJson
				+ "  \"schemas\": [\r\n"
				+ "    \"urn:ietf:params:scim:schemas:sailpoint:1.0:User\",\r\n"
				+ "    \"urn:ietf:params:scim:schemas:core:2.0:User\",\r\n"
				+ "    \"urn:ietf:params:scim:schemas:extension:enterprise:2.0:User\"\r\n"
				+ "  ]\r\n"
				+ "}";
		return asJson;
	}
}
