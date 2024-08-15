package com.employee.models;

import org.springframework.beans.factory.annotation.Value;

/**
 * EmployeeRequest
 * A model used for transferring data to the backend from the frontend
 * in a form compatible with SCIM requests.
 * Contains only the fields that are going to be required by a frontend request.
 */
public class EmployeeRequest {
	@Value("${spring.datasource.url}/Users") private static String baseUrl;
	
	//	Variables for details of SailPoint identities.
	String userName;		//	Required for POST and PUT, but cannot be changed by PUT.
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
	
	//	Constructor function for EmployeeRequest.
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
	 * toJsonString()
	 * Converts this object into a string that can be passed as a valid request body
	 * for a request to SCIM API.
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
				+ "      \"value\": \"" + managerId + "\",\r\n"
				+ "      \"$ref\": \"" + baseUrl + "/" + managerId + "\"\r\n" 
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
