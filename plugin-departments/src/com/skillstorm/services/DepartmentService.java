package com.skillstorm.services;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.skillstorm.models.Department;

import sailpoint.plugin.PluginBaseHelper;
import sailpoint.plugin.PluginContext;
import sailpoint.tools.GeneralException;
import sailpoint.tools.IOUtil;

public class DepartmentService {
	private static final String dbName = "dept_plugin";
	
	private PluginContext context;
	
	public DepartmentService(PluginContext context) {
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
	 * getAllDepartments()
	 * @return
	 * @throws GeneralException
	 */
	public List<Department> getAllDepartments() throws GeneralException {
		Connection conn = null;
		PreparedStatement stmt = null;
		try {
			ResultSet result = executeQuery(conn, stmt, "SELECT * FROM " + dbName);
			List<Department> departments = new ArrayList<>();
			while(result.next()) {
				departments.add(new Department(result.getInt("id"),
						result.getString("name"),
						result.getString("description")));
			}
			return departments;
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
	public Department getDepartmentByColumn(String col, String value) throws GeneralException {
		Connection conn = null;
		PreparedStatement stmt = null;
		try {
			ResultSet result = executeQuery(conn, stmt, "SELECT * FROM " + dbName + " WHERE " + col + " = \"" + value + "\"");
			if(result.next()) {
				Department resultDept = new Department(result.getInt("id"),
						result.getString("name"),
						result.getString("description"));
				return resultDept;
			}
			else {
				throw new GeneralException("Could not find a Department with " + col + " " + value + ".");
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
	public Department createDepartment(String name, String description) throws GeneralException {
		Connection conn = null;
		PreparedStatement stmt = null;
		try {
			//	Create new Department
			executeUpdate(conn, stmt, "INSERT INTO " + dbName + " (name, description) VALUES (\"" + name + "\", \"" + description + "\")");
			
			//	Return newly created Department
			return getDepartmentByColumn("name", name);
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
	public Department updateDepartmentByColumn(String col, String value, String name, String description) throws GeneralException {
//		System.out.println("updateDepartmentByColumn | col = " + col + " | value = " + value + " | name = " + name + " | description = " + description);
		Connection conn = null;
		PreparedStatement stmt = null;
		String changes = "";
		try {
			//	Dynamic statement depending on if name or description is null
			changes = name == null ? "" : "name = \"" + name + "\"";
			changes = changes + (description == null ? "" : (changes.length() > 0 ? ", " : "") + "description = \"" + description + "\"");
			
			//	Update existing department
			executeUpdate(conn, stmt, "UPDATE " + dbName + " SET " + changes + " WHERE " + col + " = \"" + value + "\"");

			//	Return newly updated Department
			return getDepartmentByColumn("name", name);
		} catch(Exception e) {
			throw new GeneralException("UPDATE " + dbName + " SET " + changes + " WHERE " + col + " = \"" + value + "\" \n \n " + e);
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
	public Department deleteDepartmentByColumn(String col, String value) throws GeneralException {
		Connection conn = null;
		PreparedStatement stmt = null;
		try {
			Department deleted = getDepartmentByColumn(col, value);
			if(deleted == null) {
				throw new GeneralException("Could not find a Department with " + col + " " + value + ".");
			}
			else {
				executeUpdate(conn, stmt, "DELETE FROM " + dbName + " WHERE " + col + " = \"" + value + "\"");
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
