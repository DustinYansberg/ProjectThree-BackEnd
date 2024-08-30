package com.skillstorm.services;

import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;


import sailpoint.api.SailPointContext;
import sailpoint.api.SailPointFactory;
import sailpoint.object.Attributes;
import sailpoint.object.Bundle;
import sailpoint.object.Identity;
import sailpoint.object.Link;
import sailpoint.plugin.PluginContext;
import sailpoint.tools.GeneralException;

public class AccountService {

	public AccountService(PluginContext pluginContext) {
	}
	
	
	//Service method to get Account info from SailPoint Database
	//Returns a formatted Account object
	public Map<String, Object> getAccountById(String id) throws GeneralException{
		
		//get access to objects within our application
		SailPointContext sailPointContext = SailPointFactory.getCurrentContext();
		//Link object gives access to fields needed for Account information
		Link link = sailPointContext.getObjectById(Link.class, id);
		Attributes<String, Object> att = link.getAttributes();
		
	
		//able to get Identity information from the Link object
		Identity identity = link.getIdentity();
		List<Bundle> roleAssignments = identity.getAssignedRoles();
		List<String> roles = new LinkedList<>();
		if (!roles.isEmpty()) {
			for (Bundle role: roleAssignments) {
				roles.add(role.getDisplayableName());
			}
		}
		
		Map<String, Date> meta = new HashMap<>();
		meta.put("created", link.getCreated());
		meta.put("modified", link.getModified());
		
		
		//assign fields to a formatted Map
		Map<String, Object> account = new HashMap<>();
		account.put("accountDisplayName", link.getDisplayableName());
		account.put("accountId", link.getId());
		account.put("accountAlias", att.getString("Alias"));
		account.put("isActive", att.getString("IsActive"));
		account.put("applicationDisplayName", link.getApplicationName());
		account.put("roles", roles);
		account.put("permissionSets", att.getList("PermissionSet"));
		account.put("communityNickname", att.getString("CommunityNickname"));
		account.put("email", att.getString("Email"));
		account.put("meta", meta);
		return account;
	}
	
	//Service method to get list of Accounts by an Identity ID from SailPoint Database 
	//Returns a List of Account objects
	public List<Map<String, Object>> getAccountsByIdentityId(String id) throws GeneralException{

		SailPointContext sailPointContext = SailPointFactory.getCurrentContext();
		Identity identity = sailPointContext.getObjectById(Identity.class, id);
		List<Link> links = identity.getLinks();
		List<Map<String, Object>> accounts = new LinkedList<>();
		for (Link link : links) {
			Attributes<String, Object> att = link.getAttributes();
			
			Map<String, Date> meta = new HashMap<>();
			meta.put("created", link.getCreated());
			meta.put("modified", link.getModified());

			List<Bundle> roleAssignments = identity.getAssignedRoles();
			List<String> roles = new LinkedList<>();
			if (!roles.isEmpty()) {
				for (Bundle role: roleAssignments) {
					roles.add(role.getDisplayableName());
				}
			}
			
			
			Map<String, Object> account = new HashMap<>();
			account.put("accountDisplayName", link.getDisplayableName());
			account.put("accountId", link.getId());
			account.put("accountAlias", att.getString("Alias"));
			account.put("isActive", att.getString("IsActive"));
			account.put("applicationDisplayName", link.getApplicationName());
			account.put("roles", roles);
			account.put("permissionSets", att.getList("PermissionSet"));
			account.put("communityNickname", att.getString("CommunityNickname"));
			account.put("email", att.getString("Email"));
			account.put("meta", meta);
			accounts.add(account);
		}
		return accounts;
	}
	
	//Service method to get specific information needed for updating accounts
	public Map<String, Object> getUpdateInfo(String id) throws GeneralException{

		SailPointContext sailPointContext = SailPointFactory.getCurrentContext();
		Link link = sailPointContext.getObjectById(Link.class, id);
		Attributes<String, Object> att = link.getAttributes();
		
		Identity identity = link.getIdentity();
		
		Map<String, Date> meta = new HashMap<>();
		meta.put("created", link.getCreated());
		meta.put("modified", link.getModified());
		
		Map<String, Object> account = new HashMap<>();
		account.put("attributes", att);
		account.put("NativeIdentity", link.getNativeIdentity());
		account.put("ProfileId", att.getString("ProfileId"));
		account.put("ReceivesAdminInfoEmails", att.getString("ReceivesAdminInfoEmails"));
		account.put("UserPermissionsMobileUser", att.getString("UserPermissionsMobileUser"));
		account.put("UserPermissionsOfflineUser", att.getString("UserPermissionsOfflineUser"));
		account.put("UserPermissionsSFContentUser", att.getString("UserPermissionsSFContentUser"));
		account.put("IsActive", att.getString("IsActive"));
		account.put("ProfileName", att.getString("ProfileName"));
		account.put("CompanyName", att.getString("CompanyName"));
		account.put("IsFrozen", att.getString("IsFrozen"));
		account.put("PermissionSetLicense", att.getString("PermissionSetLicense"));
		account.put("ReceivesInfoEmails", att.getString("ReceivesInfoEmails"));
		account.put("UserPermissionsMarketingUser", att.getString("UserPermissionsMarketingUser"));
		account.put("PermissionSet", att.getString("PermissionSet"));
		account.put("Country", att.getString("Country"));
		account.put("Id", att.getString("Id"));
		account.put("UserType", att.getString("UserType"));
		account.put("accountAppId", link.getApplicationId());
		account.put("identityId", identity.getId());
		account.put("firstName", identity.getFirstname());
		account.put("lastName", identity.getLastname());
		account.put("fullName", identity.getFullName());
		account.put("username", att.get("Username"));
		
		return account;
	}
	
}
