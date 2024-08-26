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
		logEntries.add(new LogEntry(result.getString("receiver_id"), result.getInt("email_id"),
			result.getDate("created_at"), result.getTime("created_at")));
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

    public int countLogs() throws GeneralException {
	Connection connection = null;
	PreparedStatement statement = null;

	try {
	    connection = pluginContext.getConnection();
	    statement = PluginBaseHelper.prepareStatement(connection, "SELECT COUNT(*) FROM email_logs");
	    ResultSet result = statement.executeQuery();
	    result.next();
	    return result.getInt("count(*)");
	} catch (SQLException e) {
	    throw new GeneralException(e);
	} finally {
	    IOUtil.closeQuietly(statement);
	    IOUtil.closeQuietly(connection);
	}
    }

    public int deleteOldLogs() throws GeneralException {

	Connection connection = null;
	PreparedStatement statement = null;

	try {
	    connection = pluginContext.getConnection();
	    /*
	     * MySQL does not support LIMIT in subqueries for some reason. pretty hilarious
	     * in my opinion. Using left join of a derived table as a workaround
	     */
	    statement = PluginBaseHelper.prepareStatement(connection,
		    "DELETE e FROM email_logs e LEFT JOIN (SELECT log_id FROM email_logs ORDER BY log_id DESC "
			    + "LIMIT 3) AS latest ON e.log_id = latest.log_id WHERE latest.log_id IS NULL;");

	    int numDeleted = statement.executeUpdate();

	    if (numDeleted <= 0) {
		throw new SQLException("Error with Deletion");
	    } else {
		return numDeleted;
	    }

	} catch (SQLException e) {
	    throw new GeneralException(e);
	} finally {
	    IOUtil.closeQuietly(statement);
	    IOUtil.closeQuietly(connection);
	}
    }

}
