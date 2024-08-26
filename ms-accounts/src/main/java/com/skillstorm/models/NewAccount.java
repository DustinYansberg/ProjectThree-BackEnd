package com.skillstorm.models;

public class NewAccount {
	
	String userId;
	String accountAppId;
	String nativeIdentity;
	String accountDisplayName;
	String appName;
	String username;
	String lastName;
	String firstName;
	String communityNickname;
	String accountAlias;
	String email;
	boolean active;
	boolean locked;
	
	
	
	static final String timeZoneSidKey = "America/Los_Angeles";
	static final String localeSidKey = "en_US";
	static final String emailEncodingKey = "UTF-8";
	static final String languageLocaleKey = "en_US";


	public NewAccount(String userId, String accountAppId, String nativeIdentity, String accountDisplayName,
			String appName, String username, String lastName, String firstName, String communityNickname, 
			String accountAlias, String email, boolean active, boolean locked) {
		super();
		this.userId = userId;
		this.accountAppId = accountAppId;
		this.nativeIdentity = nativeIdentity;
		this.accountDisplayName = accountDisplayName;
		this.appName = appName;
		this.username = username;
		this.lastName = lastName;
		this.firstName = firstName;
		this.communityNickname = communityNickname;
		this.accountAlias = accountAlias;
		this.email = email;
		this.active = active;
		this.locked = locked;
	}


	public String toJsonString() {
		String asJson = "{\r\n"
			+ "  \"identity\": {\r\n"
			+ "    \"value\": \"" + userId + "\"\r\n"
			+ "  },\r\n"
			+ "  \"application\": {\r\n"
			+ "    \"value\": \"" + accountAppId + "\"\r\n"
			+ "  },\r\n";
		if(nativeIdentity != null) {
			asJson = asJson + "  \"nativeIdentity\": \"" + nativeIdentity + "\",\r\n";
		}
		asJson = asJson
			+ "  \"displayName\": \"" + accountDisplayName + "\",\r\n"

			+ "  \"urn:ietf:params:scim:schemas:sailpoint:1.0:Application:Schema:" + appName + ":account\": {\r\n";
			asJson = asJson
				+ "    \"ProfileName\": \"Standard User\",\r\n"
				+ "    \"Username\": \"" + username + "\",\r\n"
				+ "    \"LastName\": \"" + lastName + "\",\r\n"
				+ "    \"FirstName\": \"" + firstName + "\",\r\n"
				+ "    \"CommunityNickname\": \"" + communityNickname + "\",\r\n"
				+ "    \"Alias\": \"" + accountAlias + "\",\r\n"
				+ "    \"Email\": \"" + email + "\",\r\n"
				+ "    \"TimeZoneSidKey\": \"" + timeZoneSidKey + "\",\r\n"
				+ "    \"LocaleSidKey\": \"" + localeSidKey + "\",\r\n"
				+ "    \"EmailEncodingKey\": \"" + emailEncodingKey + "\",\r\n"
				+ "    \"LanguageLocaleKey\": \"" + languageLocaleKey + "\"\r\n";
		asJson = asJson + "  },\r\n"
			+ "  \"active\": " + active + ",\r\n"
			+ "  \"locked\": " + locked + "\r\n"
			+ "}";
		return asJson;
		
		
	}
}
