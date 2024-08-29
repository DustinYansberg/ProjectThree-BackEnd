package com.document.services;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import java.util.Base64;

import com.document.models.Notification;

@Service
public class NotificationService {
	
    private final String baseUrl = "http://135.237.83.37:8080/identityiq/plugin/rest/NotificationPlugin";
    	
    private final String username = "spadmin";
    private final String password = "admin";
    private final String authString = "Basic " + Base64.getEncoder().encodeToString(("spadmin:admin").getBytes());
    
    public ResponseEntity<String> getNotificationsByIdentity(String identityId) {
        String url = baseUrl + "/notifications/" + identityId;
        
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
    
    
    public ResponseEntity<String> createNotification(Notification notification) {
    	String url = baseUrl + "/notification" ;

        RestTemplate template = new RestTemplate();
		HttpHeaders headers = new HttpHeaders();
		headers.setBasicAuth(username, password);
		HttpEntity<Notification> entity = new HttpEntity<>(notification, headers);
		 
		return		template.exchange(
						  url
						, HttpMethod.POST
						, entity
						, String.class
					
						);
        
    }
    
    public ResponseEntity<Integer> readAllNotificationsById(String identityId) {
    	String url = baseUrl + "/check/" + identityId ;

        RestTemplate template = new RestTemplate();
		HttpHeaders headers = new HttpHeaders();
		headers.setBasicAuth(username, password);
		HttpEntity<String> entity = new HttpEntity<>(headers);
		 
		return		template.exchange(
						  url
						, HttpMethod.PUT
						, entity
						, Integer.class
					
						);
        
    
    }
    
}
