package com.skillstorm.services;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.skillstorm.models.Notification;

import sailpoint.plugin.PluginBaseHelper;
import sailpoint.plugin.PluginContext;
import sailpoint.server.Environment;
import sailpoint.tools.GeneralException;
import sailpoint.tools.IOUtil;

public class NotificationService {

	private PluginContext pluginContext;

	public NotificationService(PluginContext pluginContext) {
		this.pluginContext = pluginContext;
	}

	// GeneralException is SailPoint's top-level Exception that the others inherit
	// from
	public List<Notification> getAllNotifications() throws GeneralException {

		Connection connection = null;
		PreparedStatement statement = null;

		try {

			connection = pluginContext.getConnection();

			statement = PluginBaseHelper.prepareStatement(connection, "SELECT * FROM plugin_notification");

			ResultSet result = statement.executeQuery();

			List<Notification> notifications = new ArrayList<>();

			while (result.next()) {
				Notification notification = new Notification();
				notification.setNotificationId(result.getInt("notificationId"));
				notification.setIdentityId(result.getString("identityId"));
				notification.setApplicationId(result.getString("applicationId"));
				notification.setMessage(result.getString("message"));
				notification.setChecked(result.getBoolean("checked"));
				notification.setCreatedAt(result.getTimestamp("createdAt"));
				notifications.add(notification);
			}

			return notifications;

		} catch (SQLException e) {
			throw new GeneralException(e);
		} finally {
			IOUtil.closeQuietly(statement);
			IOUtil.closeQuietly(connection);
		}

	}

	// a post method for creating a new office
	public Notification createNotification(String identityId, String applicationId) throws GeneralException {

		Map<String, String> names = this.getNamesByIds(identityId, applicationId);
		String message = this.buildNotificationMessage(names);

		Timestamp createdAt = new Timestamp(System.currentTimeMillis());

		Connection connection = null;
		PreparedStatement statement = null;

		try {
			connection = pluginContext.getConnection();
			statement = PluginBaseHelper.prepareStatement(connection,
					"INSERT INTO plugin_notification (identityId, applicationId, message, checked, createdAt) "
							+ "VALUES (?, ?, ?, false, ?)",
					identityId, applicationId, message, createdAt);

			// the line to execute the statement is different for any non-GET request
			statement.executeUpdate();

			return getNotification(identityId, applicationId);

		} catch (SQLException e) {
			throw new GeneralException(e);
		} finally {
			IOUtil.closeQuietly(statement);
			IOUtil.closeQuietly(connection);
		}

	}

	// a GET method for retrieving an office via department
	public Notification getNotification(String identityId, String applicationId) throws GeneralException {

		Connection connection = null;
		PreparedStatement statement = null;

		try {
			connection = pluginContext.getConnection();
			statement = PluginBaseHelper.prepareStatement(connection,
					"SELECT * FROM plugin_notification WHERE identityId = ? AND applicationId = ?", identityId,
					applicationId);

			ResultSet result = statement.executeQuery();

			result.next();

			Notification notification = new Notification();
			notification.setNotificationId(result.getInt("notificationId"));
			notification.setIdentityId(result.getString("identityId"));
			notification.setApplicationId(result.getString("applicationId"));
			notification.setMessage(result.getString("message"));
			notification.setChecked(result.getBoolean("checked"));
			notification.setCreatedAt(result.getTimestamp("createdAt"));

			return notification;

		} catch (SQLException e) {
			throw new GeneralException(e);
		} finally {
			IOUtil.closeQuietly(statement);
			IOUtil.closeQuietly(connection);
		}

	}

	// a GET method for retrieving an office via department
	public List<Notification> getNotificationsByIdentityId(String identityId) throws GeneralException {

		Connection connection = null;
		PreparedStatement statement = null;

		try {
			connection = pluginContext.getConnection();
			statement = PluginBaseHelper.prepareStatement(connection,
					"SELECT * FROM plugin_notification WHERE identityId = ?", identityId);

			ResultSet result = statement.executeQuery();

			List<Notification> notifications = new ArrayList<>();

			while (result.next()) {
				Notification notification = new Notification();
				notification.setNotificationId(result.getInt("notificationId"));
				notification.setIdentityId(result.getString("identityId"));
				notification.setApplicationId(result.getString("applicationId"));
				notification.setMessage(result.getString("message"));
				notification.setChecked(result.getBoolean("checked"));
				notification.setCreatedAt(result.getTimestamp("createdAt"));
				notifications.add(notification);
			}

			return notifications;

		} catch (SQLException e) {
			throw new GeneralException(e);
		} finally {
			IOUtil.closeQuietly(statement);
			IOUtil.closeQuietly(connection);
		}

	}

	public int readAllNotificationsByIdentityId(String identityId) throws GeneralException {
		Connection connection = null;
		PreparedStatement statement = null;

		try {
			connection = pluginContext.getConnection();
			statement = PluginBaseHelper.prepareStatement(connection,
					"UPDATE plugin_notification SET checked = true WHERE identityId = ?", identityId);

			return statement.executeUpdate();

		} catch (SQLException e) {
			throw new GeneralException(e);
		} finally {
			IOUtil.closeQuietly(statement);
			IOUtil.closeQuietly(connection);
		}
	}

	public String buildNotificationMessage(Map<String, String> names) {
		String managerName = names.getOrDefault("managerName", "Your Manager");
		String applicationName = names.getOrDefault("applicationName", "Application");

		return String.format("%s has created an account for you in %s.", managerName, applicationName);
	}

	public Map<String, String> getNamesByIds(String identityId, String applicationId) throws GeneralException {

		Connection connection = null;
		PreparedStatement statement = null;

		try {
			connection = Environment.getEnvironment().getSpringDataSource().getConnection();
			statement = PluginBaseHelper.prepareStatement(connection, "SELECT * FROM spt_identity WHERE id = ?",
					identityId);

			ResultSet identityResult = statement.executeQuery();
			
			boolean validIdentity = identityResult.next();

			if(validIdentity == false) {
				throw new GeneralException("Employee Missing");
			}

			Map<String, String> identityMap = new HashMap<>();

			if(identityResult.getString("manager") == null) {
				throw new GeneralException("Missing Manager");
			}
			
			identityMap.put("managerId", identityResult.getString("manager"));
				
			statement = PluginBaseHelper.prepareStatement(connection, "SELECT * FROM spt_identity WHERE id = ?",
					identityMap.get("managerId"));

			ResultSet managerResult = statement.executeQuery();

			managerResult.next();

			Map<String, String> managerMap = new HashMap<>();
			
			if(managerResult.getString("display_name") == null) {
				throw new GeneralException("Missing Manager Display Name");
			}
			
			managerMap.put("managerName", managerResult.getString("display_name"));

			statement = PluginBaseHelper.prepareStatement(connection, "SELECT * FROM spt_application WHERE id = ?",
					applicationId);

			ResultSet applicationResult = statement.executeQuery();
			
			boolean validApplication = applicationResult.next();

			if(validApplication == false) {
				throw new GeneralException("Application does not exist");
			}

			Map<String, String> applicationMap = new HashMap<>();
			
			if(applicationResult.getString("name") == null) {
				throw new GeneralException("Missing Application Name");
			}

			applicationMap.put("applicationName", applicationResult.getString("name"));

			Map<String, String> nameMap = new HashMap<>();

			nameMap.put("managerName", managerMap.getOrDefault("managerName", "Manager"));
			nameMap.put("applicationName", applicationMap.getOrDefault("applicationName", "Application"));

			return nameMap;

		} catch (SQLException e) {
			throw new GeneralException(e);
		} finally {
			IOUtil.closeQuietly(statement);
			IOUtil.closeQuietly(connection);
		}

	}

}