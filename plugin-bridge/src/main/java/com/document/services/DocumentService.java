package com.document.services;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import java.util.Base64;

import com.document.models.Document;

@Service
public class DocumentService {
	
    private final RestTemplate restTemplate = new RestTemplate();
    private final String baseUrl = "http://135.237.83.37:8080//identityiq/plugin/rest/ComplianceTrainingPlugin";
    private final String username = "spadmin";
    private final String password = "admin";
    private final String authString = "Basic " + Base64.getEncoder().encodeToString(("spadmin:admin").getBytes());
    
    		
//    public DocumentService(RestTemplate restTemplate) {
//        this.restTemplate = restTemplate;
//        
//    }

    public ResponseEntity<String> getDocumentsByIdentity(String id) {
        String url = baseUrl + "/getDocuments/" + id;
        
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
    
    public ResponseEntity<String> createDoc(Document document) {
    	String url = baseUrl + "/createDoc" ;

        RestTemplate template = new RestTemplate();
		HttpHeaders headers = new HttpHeaders();
		headers.setBasicAuth(username, password);
		HttpEntity<Document> entity = new HttpEntity<>( document, headers);
		 
		return		template.exchange(
						  url
						, HttpMethod.POST
						, entity
						, String.class
					
						);
        
    }
    
    public ResponseEntity<String> completeDoc(int id) {
    	String url = baseUrl + "/complete/" + id ;

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
    
}
