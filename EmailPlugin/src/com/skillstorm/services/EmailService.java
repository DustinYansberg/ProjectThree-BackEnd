package com.skillstorm.services;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.skillstorm.models.Email;

import sailpoint.plugin.PluginBaseHelper;
import sailpoint.plugin.PluginContext;
import sailpoint.tools.GeneralException;
import sailpoint.tools.IOUtil;

public class EmailService {

    private PluginContext pluginContext;

    public EmailService(PluginContext pluginContext) {
	this.pluginContext = pluginContext;
    }

    public List<Email> getAllEmails() throws GeneralException {

	Connection connection = null;
	PreparedStatement statement = null;

	try {
	    connection = pluginContext.getConnection();
	    statement = PluginBaseHelper.prepareStatement(connection, "SELECT * FROM email_templates");
	    ResultSet result = statement.executeQuery();
	    List<Email> emails = new ArrayList<>();

	    while (result.next()) {
		emails.add(new Email(result.getInt("id"), result.getString("name"), result.getString("subject"),
			result.getString("body"), result.getDate("created_at"), result.getDate("updated_at")));
	    }

	    return emails;
	} catch (SQLException e) {
	    throw new GeneralException(e);
	} finally {
	    IOUtil.closeQuietly(statement);
	    IOUtil.closeQuietly(connection);
	}

    }

    public Email getEmailById(int id) throws GeneralException {
	Connection connection = null;
	PreparedStatement statement = null;

	try {
	    connection = pluginContext.getConnection();
	    statement = PluginBaseHelper.prepareStatement(connection, "SELECT * FROM email_templates WHERE id = ?", id);
	    ResultSet result = statement.executeQuery();
	    Email email = new Email();

	    while (result.next()) {
		email = new Email(result.getInt("id"), result.getString("name"), result.getString("subject"),
			result.getString("body"), result.getDate("created_at"), result.getDate("updated_at"));
	    }

	    return email;
	} catch (SQLException e) {
	    throw new GeneralException(e);
	} finally {
	    IOUtil.closeQuietly(statement);
	    IOUtil.closeQuietly(connection);
	}
    }

    public int createEmail(String name, String subject, String emailBody) throws GeneralException {
	Connection connection = null;
	PreparedStatement statement = null;

	try {
	    connection = pluginContext.getConnection();
	    statement = PluginBaseHelper.prepareStatement(connection,
		    "INSERT INTO email_templates (name, subject, body) " + "VALUES (?, ?, ?)", name, subject,
		    emailBody);

	    // the line to execute the statement is different for any non-GET request
	    return statement.executeUpdate();

	} catch (SQLException e) {
	    throw new GeneralException(e);
	} finally {
	    IOUtil.closeQuietly(statement);
	    IOUtil.closeQuietly(connection);
	}

    }

}
