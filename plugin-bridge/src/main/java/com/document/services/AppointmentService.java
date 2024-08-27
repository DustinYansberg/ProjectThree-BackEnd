package com.document.services;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import com.document.models.Appointment;

@Service
public class AppointmentService {
	
    private final String baseUrl = "http://135.237.83.37:8080//identityiq/plugin/rest/AppointmentsPlugin";
    private final String username = "spadmin";
    private final String password = "admin";
    
    
    		
//    public DocumentService(RestTemplate restTemplate) {
//        this.restTemplate = restTemplate;
//        
//    }

    public ResponseEntity<String> getByOrganizerId(String id) {
        String url = baseUrl + "/getByOrganizer/" + id;
        
        RestTemplate template = new RestTemplate();
		HttpHeaders headers = new HttpHeaders();
		headers.setBasicAuth(username, password);
		HttpEntity<String> entity = new HttpEntity<>(headers);
		 
		return		template.exchange(
						  url
						, HttpMethod.GET
						, entity
						, String.class
					
						);
        
    }
    
    public ResponseEntity<String> getByAttendeeId(String id) {
        String url = baseUrl + "/getByAttendee/" + id;
        
        RestTemplate template = new RestTemplate();
		HttpHeaders headers = new HttpHeaders();
		headers.setBasicAuth(username, password);
		HttpEntity<String> entity = new HttpEntity<>(headers);
		 
		return		template.exchange(
						  url
						, HttpMethod.GET
						, entity
						, String.class
					
						);
        
    }
    
    public ResponseEntity<String> createAppointment(Appointment appointment) {
    	String url = baseUrl + "/create" ;

        RestTemplate template = new RestTemplate();
		HttpHeaders headers = new HttpHeaders();
		headers.setBasicAuth(username, password);
		HttpEntity<Appointment> entity = new HttpEntity<>( appointment, headers);
		 
		return		template.exchange(
						  url
						, HttpMethod.POST
						, entity
						, String.class
					
						);
        
    }
    
    public ResponseEntity<String> appointmentCheckIn(int id) {
    	String url = baseUrl + "/checkIn/" + id ;

        RestTemplate template = new RestTemplate();
		HttpHeaders headers = new HttpHeaders();
		headers.setBasicAuth(username, password);
		HttpEntity<String> entity = new HttpEntity<>(headers);
		 
		return		template.exchange(
						  url
						, HttpMethod.PUT
						, entity
						, String.class
					
						);
        
    
    }
    
    public ResponseEntity<String> appointmentDelete(int id) {
    	String url = baseUrl + "/deleteBy/" + id ;

        RestTemplate template = new RestTemplate();
		HttpHeaders headers = new HttpHeaders();
		headers.setBasicAuth(username, password);
		HttpEntity<String> entity = new HttpEntity<>(headers);
		 
		return		template.exchange(
						  url
						, HttpMethod.DELETE
						, entity
						, String.class
					
						);
        
    
    }
    
}
