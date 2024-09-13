package com.skillstorm.policies;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.LinkedList;
import java.util.List;


import com.skillstorm.models.Appointment;
import com.skillstorm.services.AppointmentService;

import sailpoint.api.SailPointContext;
import sailpoint.object.Identity;
import sailpoint.object.Policy;
import sailpoint.object.PolicyViolation;
import sailpoint.policy.BasePluginPolicyExecutor;
import sailpoint.tools.GeneralException;

// PolicyExecutors must extend this class to function properly
public class LateAppointmentPolicyExecutor extends BasePluginPolicyExecutor {

	@Override
	public String getPluginName() {
		return "AppointmentsPlugin";
	}
	
	// this method is what gets executed when the system checks against this policy
	// it must return a List of PolicyViolation objects
	@Override
	public List<PolicyViolation> evaluate(SailPointContext context, Policy policy, Identity identity) throws GeneralException {
		
		// a holder for all violations we're going to return
		List<PolicyViolation> violations = new LinkedList<>();
		
		// getting our service to use to find the number of current offices
		AppointmentService service = new AppointmentService(this);
		List<Appointment> attendeeAppointments = service.getAppointmentsByAttendee(identity.getId());
		
		// getting the late time
		int lateTime = policy.getInt("lateTime");
		
		// Auto delete time
		int deleteTime = policy.getInt("deleteTime");
		
		LocalDateTime zdt = LocalDateTime.now(ZoneId.of("America/New_York"));
		
		for (Appointment appointment : attendeeAppointments) {
			System.out.println(appointment.getDatetime());
			LocalDateTime appointmentTime = LocalDateTime.parse(appointment.getDatetime(), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
			
			System.out.println(appointmentTime.toString());
			System.out.println(zdt);
			if (appointmentTime.plusMinutes(lateTime).isBefore(zdt) && !appointment.isCheckedIn()) {
				violations.add(createViolation(context, policy, identity, appointment.getId(), appointment.getOrganizerId(), appointment.getTitle()));
			}
			
			if (appointmentTime.plusMinutes(deleteTime).isBefore(zdt)) {
			    service.deleteAppointmentById(appointment.getId());
			}
		}
		
		return violations;
	}
	
	// this method is here to create a PolicyViolation object that will go into the system
	public PolicyViolation createViolation(SailPointContext context, Policy policy, Identity identity, int appointmentId, String organizerId, String appointmentTitle) throws GeneralException {
		// creating an empty PolicyViolation
		PolicyViolation violation = new PolicyViolation();
		
		// adding various properties to it before returning
		violation.setStatus(PolicyViolation.Status.Open);
		violation.setIdentity(identity);
		violation.setPolicy(policy);
		violation.setAlertable(true);
		violation.setOwner(context.getObjectById(Identity.class, organizerId));
		violation.setConstraintName("Late Check-In: " + appointmentTitle);
		violation.setArgument("Appointment ID", appointmentId);
		violation.setDescription("Late Check-In for identity: " + identity.getDisplayName() + " for appointment: " + appointmentTitle);
		
		// packaging in our violation with some context
		// identity and policy are already set above
		return formatViolation(context, identity, policy, null, violation);
	}

}
