package com.skillstorm.services;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import sailpoint.api.SailPointContext;
import sailpoint.api.SailPointFactory;
import sailpoint.object.EmailTemplate;
import sailpoint.plugin.PluginBaseHelper;
import sailpoint.plugin.PluginContext;
import sailpoint.server.Environment;
import sailpoint.tools.GeneralException;
import sailpoint.tools.IOUtil;

public class EmailTemplateService {

    private PluginContext pluginContext;

    public EmailTemplateService(PluginContext pluginContext) {
	this.pluginContext = pluginContext;
    }

    public List<Map<String, String>> getAllEmailTemplates() throws GeneralException {
	SailPointContext sailPointContext = SailPointFactory.getCurrentContext();
	List<EmailTemplate> emailTemplates = sailPointContext.getObjects(EmailTemplate.class);

	List<Map<String, String>> outboundEmailTemplates = new LinkedList<>();

	for (EmailTemplate emailTemplate : emailTemplates) {
	    String id = emailTemplate.getId();
	    String name = emailTemplate.getName();
	    String subject = emailTemplate.getSubject();
	    String body = emailTemplate.getBody();

	    Map<String, String> temp = new HashMap<>();
	    temp.put("id", id);
	    temp.put("name", name);
	    temp.put("subject", subject);
	    temp.put("body", body);

	    outboundEmailTemplates.add(temp);
	}
	return outboundEmailTemplates;
    }

//    Write a service function to return an email by its id. id will be provided through the http request in the controller
    public Map<String, String> getEmailTemplateById(String id) throws GeneralException {
	Connection connection = null;
	PreparedStatement statement = null;

	try {
	    connection = Environment.getEnvironment().getSpringDataSource().getConnection();
	    statement = PluginBaseHelper.prepareStatement(connection, "SELECT * FROM spt_email_template WHERE id = ?",
		    id);

	    ResultSet result = statement.executeQuery();

	    result.next();

	    Map<String, String> emailTemplate = new HashMap<>();

	    emailTemplate.put("id", result.getString("id"));
	    emailTemplate.put("name", result.getString("name"));
	    emailTemplate.put("subject", result.getString("subject"));
	    emailTemplate.put("body", result.getString("body"));
	    return emailTemplate;

	} catch (SQLException e) {
	    throw new GeneralException(e);

	} finally {
	    IOUtil.closeQuietly(statement);
	    IOUtil.closeQuietly(connection);
	}
    }
}
