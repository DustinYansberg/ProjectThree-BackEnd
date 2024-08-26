package com.skillstorm.services;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import sailpoint.api.SailPointContext;
import sailpoint.api.SailPointFactory;
import sailpoint.object.Identity;
import sailpoint.plugin.PluginBaseHelper;
import sailpoint.plugin.PluginContext;
import sailpoint.server.Environment;
import sailpoint.tools.GeneralException;
import sailpoint.tools.IOUtil;

public class IdentityService {

    private PluginContext pluginContext;

    public IdentityService(PluginContext pluginContext) {
	this.pluginContext = pluginContext;
    }

    public List<Map<String, String>> getAllIdentities() throws GeneralException {

	SailPointContext sailPointContext = SailPointFactory.getCurrentContext();

	List<Identity> identities = sailPointContext.getObjects(Identity.class);
	List<Map<String, String>> outboundIdentities = new LinkedList<>();

	for (Identity identity : identities) {
	    if (identity.getEmail() != null) {
		String id = identity.getId();
		String name = identity.getName();
		String firstname = identity.getFirstname();
		String displayName = identity.getDisplayableName();
		Date modified = identity.getModified();

		Map<String, String> temp = new HashMap<>();

		temp.put("id", id);
		temp.put("name", name);
		temp.put("firstname", firstname);
		temp.put("modified", modified.toString());
		temp.put("displayName", displayName);

		outboundIdentities.add(temp);
	    }

	}

	return outboundIdentities;

    }

    public Map<String, String> getIdentityById(String id) throws GeneralException {

	Connection connection = null;
	PreparedStatement statement = null;

	try {
	    connection = Environment.getEnvironment().getSpringDataSource().getConnection();
	    statement = PluginBaseHelper.prepareStatement(connection, "SELECT * FROM spt_identity WHERE id = ?", id);

	    ResultSet result = statement.executeQuery();

	    result.next();

	    Map<String, String> identity = new HashMap<>();

	    identity.put("id", id);
	    identity.put("displayName", result.getString("display_name"));
	    identity.put("email", result.getString("email"));
	    return identity;

	} catch (SQLException e) {
	    throw new GeneralException(e);
	} finally {
	    IOUtil.closeQuietly(statement);
	    IOUtil.closeQuietly(connection);
	}

    }
}
