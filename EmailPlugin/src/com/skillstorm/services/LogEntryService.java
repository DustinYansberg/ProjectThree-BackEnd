package com.skillstorm.services;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.skillstorm.models.LogEntry;

import sailpoint.plugin.PluginBaseHelper;
import sailpoint.plugin.PluginContext;
import sailpoint.tools.GeneralException;
import sailpoint.tools.IOUtil;

public class LogEntryService {

    private PluginContext pluginContext;

    public LogEntryService(PluginContext pluginContext) {
	this.pluginContext = pluginContext;
    }

    public List<LogEntry> getAllLogs() throws GeneralException {
	Connection connection = null;
	PreparedStatement statement = null;

	try {
	    connection = pluginContext.getConnection();
	    statement = PluginBaseHelper.prepareStatement(connection, "SELECT * FROM email_logs");
	    ResultSet result = statement.executeQuery();
	    List<LogEntry> logEntries = new ArrayList<>();

	    while (result.next()) {
		logEntries.add(new LogEntry(result.getString("receiver_id"), result.getInt("email_id")));
	    }

	    return logEntries;
	} catch (SQLException e) {
	    throw new GeneralException(e);
	} finally {
	    IOUtil.closeQuietly(statement);
	    IOUtil.closeQuietly(connection);
	}
    }

    public int addToLog(String receiverId, String emailId) throws GeneralException {

	Connection connection = null;
	PreparedStatement statement = null;

	try {
	    connection = pluginContext.getConnection();
	    statement = PluginBaseHelper.prepareStatement(connection,
		    "INSERT INTO email_logs (receiver_id, email_id) " + "VALUES ( ?, ?)", receiverId, emailId);

	    return statement.executeUpdate();

	} catch (SQLException e) {
	    throw new GeneralException(e);
	} finally {
	    IOUtil.closeQuietly(statement);
	    IOUtil.closeQuietly(connection);
	}
    }

}
