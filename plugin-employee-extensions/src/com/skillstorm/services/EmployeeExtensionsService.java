package com.skillstorm.services;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.digester.plugins.PluginContext;

import sailpoint.plugin.PluginBaseHelper;
import sailpoint.server.Environment;
import sailpoint.tools.GeneralException;
import sailpoint.tools.IOUtil;

public class EmployeeExtensionsService {
	private PluginContext context;
	private static final String[] dbColumns = {
		"id",
//		"created",
//		"modified",
		"name",
		"description",
		"display_name",
		"firstname",
		"lastname",
		"email",
		"manager_status",
		"inactive",
		"assigned_role_summary",
		"type",
		"owner",
		"manager",
		"administrator"
	};
	
	public EmployeeExtensionsService(PluginContext context) {
		this.context = context;
	}
	
	public ArrayList<Map<String, Object>> getAll() throws GeneralException {
		Connection conn = null;
		PreparedStatement stmt = null;
		
		try {
			conn = Environment.getEnvironment().getSpringDataSource().getConnection();
			stmt = PluginBaseHelper.prepareStatement(conn, "SELECT * FROM spt_identity");
			ResultSet result = stmt.executeQuery();
			
			ArrayList<Map<String, Object>> resultList = new ArrayList<>();
			while(result.next()) {
				HashMap<String, Object> resultMap = new HashMap<>();
				for(String col : dbColumns) {
					resultMap.put(col, result.getObject(col));
				}
				resultList.add(resultMap);
			}
			return resultList;
		} catch(Exception e) {
			throw new GeneralException(e);
		} finally {
			IOUtil.closeQuietly(stmt);
			IOUtil.closeQuietly(conn);
		}
	}
	
	private void debugPrint(String str) {
		System.out.println("DEBUG MESSAGE: -------------------- " + str);
	}

	/* TODO Find how password encryption/decryption works in SailPoint.
	 * 		As of now, passwords retrieved are in encrypted form, so they cannot be checked with old passwords until decrypted.
	 * 		Passwords stored are unencrypted until used to log in, so they must be encrypted by the method first.		
	 */
	private static final boolean checkForOldPassword = false;
    public String updatePasswordDirectly(String id, String oldPassword, String newPassword) throws GeneralException {
    	debugPrint("0.1");
    	Connection conn = null;
    	PreparedStatement stmt = null;
    	try {
    		debugPrint("1.1");
    		conn = Environment.getEnvironment().getSpringDataSource().getConnection();
    		
    		//	Step 0: Check if the ID is valid.
    		stmt = PluginBaseHelper.prepareStatement(conn, "SELECT * FROM spt_identity WHERE id = ?", id);
    		ResultSet existingUser = stmt.executeQuery();
    		debugPrint("1.2");
    		if(!existingUser.next()) {
    			throw new GeneralException("No user found with ID " + id);
    		}
    		
    		//	Step 1: Check if old password is correct if we decide to
    		debugPrint("1.3: " + existingUser.toString());
    		if(checkForOldPassword) {
	    		String oldPwdCheck = existingUser.getString("password");
        		debugPrint("2.1: " + oldPwdCheck);
	    		
	    		if(!oldPassword.equals(oldPwdCheck) || !(oldPassword == null && oldPwdCheck == null)) {
	    			throw new IllegalArgumentException("Existing password is incorrect.");
	    		}
    		}
    		
    		debugPrint("1.4");
    		// Step 2: Update password
    		//	TODO Read about the encryption method used by SailPoint for passwords stored in the DB.
    		stmt = PluginBaseHelper.prepareStatement(conn, "UPDATE spt_identity SET password = ? WHERE id = ?", newPassword, id);
    		stmt.executeUpdate();
    		
    		debugPrint("1.5");
    		return "Successfully updated password.";
    	} catch(Exception e) {
    		throw new GeneralException("An error occurred during Service method processing: " + e);
    	} finally {
    		IOUtil.closeQuietly(conn);
    		IOUtil.closeQuietly(stmt);
    	}
    }
    
}
