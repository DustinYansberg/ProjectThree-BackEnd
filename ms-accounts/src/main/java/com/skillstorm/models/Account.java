package com.skillstorm.models;

import java.util.Map;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.skillstorm.models.Meta;
import org.springframework.beans.factory.annotation.Value;

public class Account {
	@Value("${spring.datasource.url}") private static String baseUrl;
//	  String accountDisplayName;

//	  String accountAlias;

//	  String applicationDisplayName;
//	  String[] roles;
//	  String[] permissionSets;
//	  String communityNickname;

//	  String email;
//	  Meta meta;
	@JsonProperty("accountDisplayName")
	String accountDisplayName;
	@JsonProperty("accountId")
	String accountId;
	@JsonProperty("accountAlias")
	String accountAlias;	
	@JsonProperty("isActive")
	boolean isActive;
	@JsonProperty("applicationDisplayName")
	String applicationDisplayName;
	@JsonProperty("roles")
	String[] roles;
	@JsonProperty("permissionSets")
	String[] permissionSets;
	@JsonProperty("communityNickname")
	String communityNickname;
	@JsonProperty("jobTitle")
	String jobTitle;
	@JsonProperty("email")
	String email;
	@JsonProperty("meta")
	Meta meta;
	
	
	
	
	static final String timeZoneSidKey = "America/Los_Angeles";
	static final String localeSidKey = "en_US";
	static final String emailEncodingKey = "UTF-8";
	static final String languageLocaleKey = "en_US";
	
	
	String[] permissions;

	public Account() {
		super();

	}

	public Account(String accountAlias, String accountDisplayName, String accountId, Meta meta, String[] roles,
			String[] permissionSets, String communityNickname, String jobTitle, boolean isActive,  
			String applicationDisplayName, String email) {
		super();
		this.accountDisplayName = accountDisplayName;
		this.applicationDisplayName = applicationDisplayName;
		this.permissionSets = permissionSets;
		this.roles = roles;
		this.communityNickname = communityNickname;
		this.accountAlias = accountAlias;
		this.isActive = isActive;
		this.email = email;
		this.accountId = accountId;
		this.meta = meta;
		this.jobTitle = jobTitle;
	
	}

	/**
	 * toJsonString()
	 * Converts this Account object into a string that can be passed as a valid request body
	 * for a request to SCIM API.
	 * @return JSON string containing Account details, formatted for SCIM requests
	 */
	public String toJsonString(Map<String, Object> info) {
		String permissions = "[";

		for(String item : this.permissionSets) {
			permissions += item+", ";
		}
		permissions = permissions.substring(0, permissions.length()-2);
		String asJson = "{\r\n"
			+ "  \"identity\": {\r\n"
			+ "    \"value\": \"" + info.get("identityId") + "\"\r\n"
			+ "  },\r\n"
			+ "  \"application\": {\r\n"
			+ "    \"value\": \"" + info.get("accountAppId") + "\"\r\n"
			+ "  },\r\n"
			+ "    \"id\": \"c0a800b690a117bc8190a23259cf00f3\",\r\n";
		if(info.get("NativeIdentity") != null) {
			asJson = asJson + "  \"nativeIdentity\": \"" + info.get("NativeIdentity") + "\",\r\n";
		}
		asJson = asJson
			+ "  \"urn:ietf:params:scim:schemas:sailpoint:1.0:Application:Schema:" + applicationDisplayName + ":account\": {\r\n";
		//if(accountAppName.equals("Salesforce")) {
			asJson = asJson
				+ "    \"ProfileId\": \""+ info.get("ProfileId") +"\",\r\n"
				+ "    \"ReceivesAdminInfoEmails\": \""+info.get("ReceivesAdminInfoEmails")+"\",\r\n"
				+ "    \"CollaborationGroup\": [],\r\n"
				+ "    \"Email\": \"" + email + "\",\r\n"
				+ "    \"UserPermissionsMobileUser\": \""+info.get("UserPermissionsMobileUser")+"\",\r\n"
				+ "    \"UserPermissionsOfflineUser\": \""+info.get("UserPermissionsOfflineUser")+"\",\r\n"
				+ "    \"UserPermissionsSFContentUser\": \""+info.get("UserPermissionsSFContentUser")+"\",\r\n"
				+ "    \"LanguageLocaleKey\": \"" + languageLocaleKey + "\",\r\n"
				+ "    \"TimeZoneSidKey\": \"" + timeZoneSidKey + "\",\r\n"
				+ "    \"IsActive\": \""+isActive+"\",\r\n"
				+ "    \"ProfileName\": \""+info.get("ProfileName")+"\",\r\n"
				+ "    \"UserLicense\": \"" + applicationDisplayName + "\",\r\n"
				+ "    \"Name\": \"" +  info.get("fullName")  + "\",\r\n"
				+ "    \"CompanyName\": \""+ info.get("CompanyName") +"\",\r\n"
				+ "    \"CommunityNickname\": \"" + communityNickname + "\",\r\n"
				+ "    \"IsFrozen\": \""+info.get("IsFrozen")+"\",\r\n"
				+ "    \"PermissionSetGroup\": [],\r\n"
				+ "    \"LocaleSidKey\": \"" + localeSidKey + "\",\r\n"
				+ "    \"FirstName\": \"" + info.get("firstName") + "\",\r\n"
				+ "    \"PermissionSetLicense\": " + info.get("PermissionSetLicense") + ",\r\n"
				+ "    \"EmailEncodingKey\": \"" + emailEncodingKey + "\",\r\n"
				+ "    \"ReceivesInfoEmails\": \""+info.get("ReceivesInfoEmails")+"\",\r\n"
				+ "    \"UserPermissionsMarketingUser\": \""+info.get("UserPermissionsMarketingUser")+"\",\r\n"
				+ "    \"ManagedPackage\": [],\r\n"
				+ "    \"PublicGroups\": [],\r\n"
				+ "    \"QueueNames\": [],\r\n"
				+ "    \"Username\": \"" + info.get("username") + "\",\r\n"
				+ "    \"PermissionSet\": "+ permissions + "],\r\n"
				+ "    \"Alias\": \"" + accountAlias + "\",\r\n"	
				+ "    \"Country\": \""+info.get("Country")+"\",\r\n"
				+ "    \"Id\": \""+info.get("Id")+"\",\r\n"
				+ "    \"LastName\": \"" + info.get("lastName") + "\",\r\n"
				+ "    \"UserType\": \""+info.get("UserType")+"\" }\r\n"
				+ "}"
				;
		//}
		return asJson;
		
		
	}
	public String toJsonStringWithPermissions(Map<String, Object> info, String[] permissionSet) {
		String permissions = "[";

		for(String item : permissionSet) {
			permissions += item+", ";
		}
		permissions = permissions.substring(0, permissions.length()-2);
		String asJson = "{\r\n"
			+ "  \"identity\": {\r\n"
			+ "    \"value\": \"" + info.get("identityId") + "\"\r\n"
			+ "  },\r\n"
			+ "  \"application\": {\r\n"
			+ "    \"value\": \"" + info.get("accountAppId") + "\"\r\n"
			+ "  },\r\n"
			+ "    \"id\": \"c0a800b690a117bc8190a23259cf00f3\",\r\n";
		if(info.get("NativeIdentity") != null) {
			asJson = asJson + "  \"nativeIdentity\": \"" + info.get("NativeIdentity") + "\",\r\n";
		}
		asJson = asJson
			+ "  \"urn:ietf:params:scim:schemas:sailpoint:1.0:Application:Schema:" + applicationDisplayName + ":account\": {\r\n";
		//if(accountAppName.equals("Salesforce")) {
			asJson = asJson
				+ "    \"ProfileId\": \""+ info.get("ProfileId") +"\",\r\n"
				+ "    \"ReceivesAdminInfoEmails\": \""+info.get("ReceivesAdminInfoEmails")+"\",\r\n"
				+ "    \"CollaborationGroup\": [],\r\n"
				+ "    \"Email\": \"" + email + "\",\r\n"
				+ "    \"UserPermissionsMobileUser\": \""+info.get("UserPermissionsMobileUser")+"\",\r\n"
				+ "    \"UserPermissionsOfflineUser\": \""+info.get("UserPermissionsOfflineUser")+"\",\r\n"
				+ "    \"UserPermissionsSFContentUser\": \""+info.get("UserPermissionsSFContentUser")+"\",\r\n"
				+ "    \"LanguageLocaleKey\": \"" + languageLocaleKey + "\",\r\n"
				+ "    \"TimeZoneSidKey\": \"" + timeZoneSidKey + "\",\r\n"
				+ "    \"IsActive\": \""+isActive+"\",\r\n"
				+ "    \"ProfileName\": \""+info.get("ProfileName")+"\",\r\n"
				+ "    \"UserLicense\": \"" + applicationDisplayName + "\",\r\n"
				+ "    \"Name\": \"" +  info.get("fullName")  + "\",\r\n"
				+ "    \"CompanyName\": \""+ info.get("CompanyName") +"\",\r\n"
				+ "    \"CommunityNickname\": \"" + communityNickname + "\",\r\n"
				+ "    \"IsFrozen\": \""+info.get("IsFrozen")+"\",\r\n"
				+ "    \"PermissionSetGroup\": [],\r\n"
				+ "    \"LocaleSidKey\": \"" + localeSidKey + "\",\r\n"
				+ "    \"FirstName\": \"" + info.get("firstName") + "\",\r\n"
				+ "    \"PermissionSetLicense\": " + info.get("PermissionSetLicense") + ",\r\n"
				+ "    \"EmailEncodingKey\": \"" + emailEncodingKey + "\",\r\n"
				+ "    \"ReceivesInfoEmails\": \""+info.get("ReceivesInfoEmails")+"\",\r\n"
				+ "    \"UserPermissionsMarketingUser\": \""+info.get("UserPermissionsMarketingUser")+"\",\r\n"
				+ "    \"ManagedPackage\": [],\r\n"
				+ "    \"PublicGroups\": [],\r\n"
				+ "    \"QueueNames\": [],\r\n"
				+ "    \"Username\": \"" + info.get("username") + "\",\r\n"
				+ "    \"PermissionSet\": "+ permissions + "],\r\n"
				+ "    \"Alias\": \"" + accountAlias + "\",\r\n"	
				+ "    \"Country\": \""+info.get("Country")+"\",\r\n"
				+ "    \"Id\": \""+info.get("Id")+"\",\r\n"
				+ "    \"LastName\": \"" + info.get("lastName") + "\",\r\n"
				+ "    \"UserType\": \""+info.get("UserType")+"\" }\r\n"
				+ "}"
				;
		//}

		return asJson;
		
		
	}
}


	


	  
	  

