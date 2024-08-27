package com.skillstorm.controllers;

import java.util.Base64;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/account")
@CrossOrigin(origins = "*")
public class AccountController {
	@Value("${spring.datasource.username}") private String username;
	@Value("${spring.datasource.password}") private String password;
	@Value("${spring.datasource.url}/account") private String baseUrl;

	private HttpHeaders generateAuthHeaders() {
		HttpHeaders headers = new HttpHeaders();
		headers.add("Authorization", "Basic " + Base64.getEncoder().encodeToString((username + ":" + password).getBytes()));
		return headers;
	}
	private ResponseEntity<Object> sendRestTemplateExchange(Object body, String url, HttpMethod method) {
		RestTemplate temp = new RestTemplate();
		HttpEntity<Object> entity = new HttpEntity<>(body, generateAuthHeaders());
		return temp.exchange(url, method, entity, Object.class);
	}
	
	@GetMapping
	public ResponseEntity<Object> test(){
		try {
			return sendRestTemplateExchange(null, baseUrl, HttpMethod.GET);
		}catch (Exception e) {
			return ResponseEntity.status(500).header(e.getMessage()).body(e.getMessage());
		}
	}
	
	@GetMapping("/identity/{id}")
	public ResponseEntity<Object> getAccountsByIdentity(@PathVariable String id){
		try {
			return sendRestTemplateExchange(null, baseUrl + "/byIdentity/" + id, HttpMethod.GET);
		} catch(Exception e) {
			return ResponseEntity.status(500).header(e.getMessage()).body(e.getMessage());
		}
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Object> getAccountById(@PathVariable String id) {
		try {
			return sendRestTemplateExchange(null, baseUrl + "/get/" + id, HttpMethod.GET);
		} catch(Exception e) {
			return ResponseEntity.status(500).header(e.getMessage()).body(e.getMessage());
		}
	}
}
