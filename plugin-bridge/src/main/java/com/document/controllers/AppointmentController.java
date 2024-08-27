package com.document.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.document.models.Appointment;
import com.document.services.AppointmentService;

@RestController
@RequestMapping("/appointment")
@CrossOrigin(origins = "*")
public class AppointmentController {

    private final AppointmentService appointmentService;

    public AppointmentController(AppointmentService appointmentService) {
    	this.appointmentService = appointmentService;
    }

    @GetMapping("/getByOrganizerId/{id}")
    public ResponseEntity<String> getByOrganizerId(@PathVariable String id) {
	return appointmentService.getByOrganizerId(id);
    }
    
    @GetMapping("/getByAttendeeId/{id}")
    public ResponseEntity<String> getByAttendeeId(@PathVariable String id) {
	return appointmentService.getByAttendeeId(id);
    }

    @PostMapping("/create")
    public ResponseEntity<String> createAppointment(@RequestBody Appointment appointment) {
	return appointmentService.createAppointment(appointment);
    }

    @PutMapping("/checkin/{id}")
    public ResponseEntity<String> appointmentCheckIn(@PathVariable int id) {
	return appointmentService.appointmentCheckIn(id);
    }
    
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> appointmentDelete(@PathVariable int id) {
    	return appointmentService.appointmentDelete(id);
    }
    
}
