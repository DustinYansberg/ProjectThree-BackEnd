package com.skillstorm.rest;


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
import javax.ws.rs.core.MediaType;

import com.skillstorm.models.Appointment;
import com.skillstorm.services.AppointmentService;

import sailpoint.rest.plugin.AllowAll;
import sailpoint.rest.plugin.BasePluginResource;
import sailpoint.tools.GeneralException;

@Path("AppointmentsPlugin")
public class AppointmentResource extends BasePluginResource {

	@Override
	public String getPluginName() {return "AppointmentsPlugin";}
	
	private AppointmentService service() {return new AppointmentService(this);}
	
	/**
	 * getAllDepartments()
	 * @return
	 * @throws GeneralException
	 */
	@GET
	@Path("getAll")
	@AllowAll
	@Produces(MediaType.APPLICATION_JSON)
	public List<Appointment> getAllAppointments() throws GeneralException {
		return service().getAllAppointments();
	}
	
	/**
	 * getDepartmentById()
	 * @param deptName
	 * @return
	 * @throws GeneralException
	 */
	@GET
	@Path("getBy/{id}")
	@AllowAll
	@Produces(MediaType.APPLICATION_JSON)
	public Appointment getAppointmentByColumn(@PathParam("id") String id) throws GeneralException {
		return service().getAppointmentById(id);
	}
	
	@GET
	@Path("getByOrganizer/{organizerId}")
	@AllowAll
	@Produces(MediaType.APPLICATION_JSON)
	public List<Appointment> getAppointmentByOrganizer(@PathParam("organizerId") String organizerId) throws GeneralException {
		return service().getAppointmentsByOrganizer(organizerId);
	}
	
	@GET
	@Path("getByAttendee/{attendeeId}")
	@AllowAll
	@Produces(MediaType.APPLICATION_JSON)
	public List<Appointment> getAppointmentByAttendee(@PathParam("attendeeId") String attendeeId) throws GeneralException {
		return service().getAppointmentsByOrganizer(attendeeId);
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
	public Appointment createAppointment(Map<String, String> body) throws GeneralException {
		if(body.get("title") == null || body.get("description") == null || body.get("datetime") == null || body.get("organizerId") == null || body.get("attendeeId") == null) {
			throw new GeneralException("Missing parameters");
		}
		else {
			return service().createAppointment(body.get("title"), body.get("description"), body.get("datetime"), body.get("organizerId"), body.get("attendeeId"));
		}
	}
	
	@PUT
	@Path("updateBy/{id}")
	@AllowAll
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Appointment updateAppointmentById(Map<String, String> body, @PathParam("id") String id) throws GeneralException {
		if(id == null) {
			throw new GeneralException("Invalid ID");
		}
		else {
			return service().updateAppointmentById(id, body.get("title"), body.get("description"), body.get("datetime"), body.get("organizerId"), body.get("attendeeId")); 
		}
	}
	
	@PUT
	@Path("checkIn/{id}")
	@AllowAll
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Appointment appointmentCheckIn(@PathParam("id") String id) throws GeneralException {
		if(id == null) {
			throw new GeneralException("Invalid ID");
		}
		else {
			return service().appointmentCheckIn(id);
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
	@Path("deleteBy/{id}")
	@AllowAll
	@Produces(MediaType.APPLICATION_JSON)
	public Appointment deleteDepartmentById(@PathParam("id") String id) throws GeneralException {
		return service().deleteAppointmentById(id);
	}
	
}
