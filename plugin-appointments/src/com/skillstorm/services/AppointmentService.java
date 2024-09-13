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
	 * executeQuery()
	 * Modularization of query execution.
	 * @Column conn
	 * @param stmt
	 * @param query
	 * @return
	 * @throws GeneralException
	 * @throws SQLException
	 */
	private ResultSet executeQuery(Connection conn, PreparedStatement stmt, String query) throws GeneralException, SQLException {
		conn = context.getConnection();
		stmt = PluginBaseHelper.prepareStatement(conn, query);
		return stmt.executeQuery();
	}
	
	/**
	 * executeUpdate()
	 * Modularization of update execution.
	 * @Column conn
	 * @param stmt
	 * @param query
	 * @throws GeneralException
	 * @throws SQLException
	 */
	private void executeUpdate(Connection conn, PreparedStatement stmt, String query) throws GeneralException, SQLException {
		conn = context.getConnection();
		stmt = PluginBaseHelper.prepareStatement(conn, query);
		stmt.executeUpdate();
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
			ResultSet result = executeQuery(conn, stmt, "SELECT * FROM " + dbName);
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
			ResultSet result = executeQuery(conn, stmt, "SELECT * FROM " + dbName + " WHERE " + col + " = \"" + value + "\"");
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
			ResultSet result = executeQuery(conn, stmt, "SELECT * FROM " + dbName + " WHERE organizerId = \"" + organizerId +"\"");
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
			ResultSet result = executeQuery(conn, stmt, "SELECT * FROM " + dbName + " WHERE attendeeId = \"" + attendeeId + "\"");
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
	public Appointment getAppointmentById(int id) throws GeneralException {
		Connection conn = null;
		PreparedStatement stmt = null;
		try {
			ResultSet result = executeQuery(conn, stmt, "SELECT * FROM " + dbName + " WHERE id = " + id);
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
			//	Create new Department
			executeUpdate(conn, stmt, "INSERT INTO " + dbName + " (title, description, datetime, organizerId, attendeeId) VALUES"
					+ " (\"" + title + "\", \"" + description + "\", \"" + datetime + "\", \"" + organizerId + "\", \"" + attendeeId + "\")");
			
			//	Return newly created Department
			return getAppointmentByColumn("title", title);
		} catch(Exception e) {
			throw new GeneralException(e);
		} finally {
			IOUtil.closeQuietly(stmt);
			IOUtil.closeQuietly(conn);
		}
	}
	
	/**
	 * updateDepartmentByColumn()
	 * @param col
	 * @param value
	 * @param name
	 * @param description
	 * @return
	 * @throws GeneralException
	 */
	public Appointment updateAppointmentById(int id, String title, String description, String datetime, String organizerId, String attendeeId) throws GeneralException {
		Connection conn = null;
		PreparedStatement stmt = null;
		try {
			//	Update existing department
			executeUpdate(conn, stmt, "UPDATE " + dbName + " SET title = \"" + title + "\" WHERE id = " + id);
			executeUpdate(conn, stmt, "UPDATE " + dbName + " SET description = \"" + description + "\" WHERE id = " + id);
			executeUpdate(conn, stmt, "UPDATE " + dbName + " SET datetime = \"" + datetime + "\" WHERE id = " + id);
			executeUpdate(conn, stmt, "UPDATE " + dbName + " SET organizerId = \"" + organizerId + "\" WHERE id = " + id);
			executeUpdate(conn, stmt, "UPDATE " + dbName + " SET attendeeId = \"" + attendeeId + "\" WHERE id = " + id);

			//	Return newly updated Department
			return getAppointmentById(id);
		} catch(Exception e) {
			throw new GeneralException(e);
		} finally {
			IOUtil.closeQuietly(stmt);
			IOUtil.closeQuietly(conn);
		}
	}
	
	public Appointment appointmentCheckIn(int id) throws GeneralException {
		Connection conn = null;
		PreparedStatement stmt = null;
		try {
			executeUpdate(conn, stmt, "UPDATE " + dbName + " SET checkedIn = 1 WHERE id = " + id);

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
	public Appointment deleteAppointmentById(int id) throws GeneralException {
		Connection conn = null;
		PreparedStatement stmt = null;
		try {
			
			Appointment deleted = getAppointmentById(id);
			if(deleted == null) {
				throw new GeneralException("Could not find a Appointment with id " + id);
			}
			else {
				executeUpdate(conn, stmt, "DELETE FROM " + dbName + " WHERE id = " + id);
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
