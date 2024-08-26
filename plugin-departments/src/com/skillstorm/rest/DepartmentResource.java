package com.skillstorm.rest;

import java.awt.PageAttributes.MediaType;
import java.util.List;
import java.util.Map;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import com.skillstorm.models.Department;
import com.skillstorm.services.DepartmentService;

import sailpoint.rest.plugin.AllowAll;
import sailpoint.rest.plugin.BasePluginResource;
import sailpoint.tools.GeneralException;

@Path("DepartmentsPlugin")
public class DepartmentResource extends BasePluginResource {
	
	@Override
	public String getPluginName() {return "DepartmentsPlugin";}
	
	private DepartmentService service() {return new DepartmentService(this);}
	
	/**
	 * getAllDepartments()
	 * @return
	 * @throws GeneralException
	 */
	@GET
	@Path("getAll")
	@AllowAll
	@Produces(MediaType.APPLICATION_JSON)
	public List<Department> getAllDepartments() throws GeneralException {
		return service().getAllDepartments();
	}
	
	/**
	 * getDepartmentByColumn()
	 * @param deptName
	 * @return
	 * @throws GeneralException
	 */
	@GET
	@Path("getBy/{col}/{value}")
	@AllowAll
	@Produces(MediaType.APPLICATION_JSON)
	public Department getDepartmentByColumn(@PathParam("col") String col, @PathParam("value") String value) throws GeneralException {
		return service().getDepartmentByColumn(col, value);
	}
	
	/**
	 * createDepartment()
	 * @param body
	 * @return
	 * @throws GeneralException
	 */
	//	TODO Test this returning a Department.
	@POST
	@Path("create")
	@AllowAll
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Department createDepartment(Map<String, String> body) throws GeneralException {
		if(body.get("name") == null || body.get("description") == null) {
			throw new GeneralException("Missing parameters. Please include name and description.");
		}
		else {
			return service().createDepartment(body.get("name"), body.get("description"));
		}
	}
	
	@PUT
	@Path("updateBy/{col}/{value}")
	@AllowAll
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Department updateDepartmentByColumn(Map<String, String> body, @PathParam("col") String col, @PathParam("value") String value) throws GeneralException {
		if(body.get("name") == null && body.get("description") == null) {
			throw new GeneralException("Missing parameters. Please include name and/or description.");
		}
		else {
			return service().updateDepartmentByColumn(col, value, body.get("name"), body.get("description")); 
		}
	}
	
	/**
	 * deleteDepartmentByColumn()
	 * @param id
	 * @return
	 * @throws GeneralException
	 */
	//	FIXME Currently returns null. I do not know why.
	@DELETE
	@Path("deleteBy/{col}/{value}")
	@AllowAll
	@Produces(MediaType.APPLICATION_JSON)
	public Department deleteDepartmentByColumn(@PathParam("col") String col, @PathParam("value") String value) throws GeneralException {
		return service().deleteDepartmentByColumn(col, value);
	}
}
