package com.skillstorm.services;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.skillstorm.models.Appointment;

import sailpoint.plugin.PluginBaseHelper;
import sailpoint.plugin.PluginContext;
import sailpoint.tools.GeneralException;
import sailpoint.tools.IOUtil;

public class AppointmentService {
	private static final String dbName = "appointment_plugin";
	
	private PluginContext context;
	
	public AppointmentService(PluginContext context) {
		this.context = context;
	}
	
	/**
	 * getAllAppointments()
	 * @return
	 * @throws GeneralException
	 */
	public List<Appointment> getAllAppointments() throws GeneralException {
		Connection conn = null;
		PreparedStatement stmt = null;
		try {
			conn = context.getConnection();
			stmt = PluginBaseHelper.prepareStatement(conn, "SELECT * FROM " + dbName);
			ResultSet result = stmt.executeQuery();
			List<Appointment> appointments = new ArrayList<>();
			while(result.next()) {
				appointments.add(new Appointment(result.getInt("id"),
						result.getString("title"),
						result.getString("description"),
						result.getString("datetime"),
						result.getString("organizerId"),
						result.getString("attendeeId"),
						result.getBoolean("checkedIn")));
			}
			return appointments;
		} catch(Exception e) {
			throw new GeneralException(e);
		} finally {
			IOUtil.closeQuietly(stmt);
			IOUtil.closeQuietly(conn);
		}
	}
	
	/**
	 * getDepartmentByColumn()
	 * Modularization of get one function.
	 * @param column
	 * @param value
	 * @return
	 * @throws GeneralException
	 */
	public Appointment getAppointmentByColumn(String col, String value) throws GeneralException {
		Connection conn = null;
		PreparedStatement stmt = null;
		try {
			conn = context.getConnection();
			stmt = PluginBaseHelper.prepareStatement(conn, "SELECT * FROM " + dbName + " WHERE " + col + " = \"" + value + "\"");
			ResultSet result = stmt.executeQuery();
			if(result.next()) {
				Appointment resultDept = new Appointment(result.getInt("id"),
						result.getString("title"),
						result.getString("description"),
						result.getString("datetime"),
						result.getString("organizerId"),
						result.getString("attendeeId"),
						result.getBoolean("checkedIn"));
				return resultDept;
			}
			else {
				throw new GeneralException("Could not find a Appointment with " + col + " " + value + ".");
			}
		} catch(Exception e) {
			throw new GeneralException(e);
		} finally {
			IOUtil.closeQuietly(stmt);
			IOUtil.closeQuietly(conn);
		}
	}
	
	public List<Appointment> getAppointmentsByOrganizer(String organizerId) throws GeneralException {
		Connection conn = null;
		PreparedStatement stmt = null;
		try {
			conn = context.getConnection();
			stmt = PluginBaseHelper.prepareStatement(conn, "SELECT * FROM " + dbName + " WHERE organizerId = \"" + organizerId +"\"");
			ResultSet result = stmt.executeQuery();
			List<Appointment> appointments = new ArrayList<>();
			while(result.next()) {
				appointments.add(new Appointment(result.getInt("id"),
						result.getString("title"),
						result.getString("description"),
						result.getString("datetime"),
						result.getString("organizerId"),
						result.getString("attendeeId"),
						result.getBoolean("checkedIn")));
			}
			return appointments;
		} catch(Exception e) {
			throw new GeneralException(e);
		} finally {
			IOUtil.closeQuietly(stmt);
			IOUtil.closeQuietly(conn);
		}
	}
	
	public List<Appointment> getAppointmentsByAttendee(String attendeeId) throws GeneralException {
		Connection conn = null;
		PreparedStatement stmt = null;
		try {
			conn = context.getConnection();
			stmt = PluginBaseHelper.prepareStatement(conn, "SELECT * FROM " + dbName + " WHERE attendeeId = \"" + attendeeId + "\"");
			ResultSet result = stmt.executeQuery();
			List<Appointment> appointments = new ArrayList<>();
			while(result.next()) {
				appointments.add(new Appointment(result.getInt("id"),
						result.getString("title"),
						result.getString("description"),
						result.getString("datetime"),
						result.getString("organizerId"),
						result.getString("attendeeId"),
						result.getBoolean("checkedIn")));
			}
			return appointments;
		} catch(Exception e) {
			throw new GeneralException(e);
		} finally {
			IOUtil.closeQuietly(stmt);
			IOUtil.closeQuietly(conn);
		}
	}
	
	/**
	 * getDepartmentByColumn()
	 * Modularization of get one function.
	 * @param column
	 * @param value
	 * @return
	 * @throws GeneralException
	 */
	public Appointment getAppointmentById(String id) throws GeneralException {
		Connection conn = null;
		PreparedStatement stmt = null;
		try {
			conn = context.getConnection();
			stmt = PluginBaseHelper.prepareStatement(conn, "SELECT * FROM " + dbName + " WHERE id = " + id);
			ResultSet result = stmt.executeQuery();
			if(result.next()) {
				Appointment resultDept = new Appointment(result.getInt("id"),
						result.getString("title"),
						result.getString("description"),
						result.getString("datetime"),
						result.getString("organizerId"),
						result.getString("attendeeId"),
						result.getBoolean("checkedIn"));
				return resultDept;
			}
			else {
				throw new GeneralException("Could not find a Appointment with id " + id);
			}
		} catch(Exception e) {
			throw new GeneralException(e);
		} finally {
			IOUtil.closeQuietly(stmt);
			IOUtil.closeQuietly(conn);
		}
	}
	
	/**
	 * createDepartment()
	 * @param name
	 * @param description
	 * @return
	 * @throws GeneralException
	 */
	public Appointment createAppointment(String title, String description, String datetime, String organizerId, String attendeeId) throws GeneralException {
		Connection conn = null;
		PreparedStatement stmt = null;
		try {
			conn = context.getConnection();
			stmt = PluginBaseHelper.prepareStatement(conn, "INSERT INTO " + dbName + " (title, description, datetime, organizerId, attendeeId) VALUES"
					+ " (\"" + title + "\", \"" + description + "\", \"" + datetime + "\", \"" + organizerId + "\", \"" + attendeeId + "\")");
			stmt.executeUpdate();
			
			//	Return newly created Department
			return getAppointmentByColumn("title", title);
		} catch(Exception e) {
			throw new GeneralException(e);
		} finally {
			IOUtil.closeQuietly(stmt);
			IOUtil.closeQuietly(conn);
		}
	}
	
	public Appointment appointmentCheckIn(String id) throws GeneralException {
		Connection conn = null;
		PreparedStatement stmt = null;
		try {
			conn = context.getConnection();
			stmt = PluginBaseHelper.prepareStatement(conn, "UPDATE " + dbName + " SET checkedIn = 1 WHERE id = " + id);
			stmt.executeUpdate();
			//	Return newly updated Department
			return getAppointmentById(id);
		} catch(Exception e) {
			throw new GeneralException(e);
		} finally {
			IOUtil.closeQuietly(stmt);
			IOUtil.closeQuietly(conn);
		}
	}
	
	/**
	 * deleteDepartmentByColumn()
	 * Modularization of delete one function.
	 * @param col
	 * @param value
	 * @return
	 * @throws GeneralException
	 */
	public Appointment deleteAppointmentById(String id) throws GeneralException {
		Connection conn = null;
		PreparedStatement stmt = null;
		try {
			
			Appointment deleted = getAppointmentById(id);
			if(deleted == null) {
				throw new GeneralException("Could not find a Department with id " + id);
			}
			else {
				conn = context.getConnection();
				stmt = PluginBaseHelper.prepareStatement(conn, "DELETE FROM " + dbName + " WHERE id = " + id);
				stmt.executeUpdate();
				return deleted;
			}
		} catch(Exception e) {
			throw new GeneralException(e);
		} finally {
			IOUtil.closeQuietly(stmt);
			IOUtil.closeQuietly(conn);
		}
	}
}
