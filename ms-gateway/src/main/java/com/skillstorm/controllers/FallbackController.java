package com.skillstorm.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/fallback")
@CrossOrigin(origins = "*")
public class FallbackController {
	
	
	@GetMapping
	public ResponseEntity<String> defaultFallback(){
		return new ResponseEntity<String>("Default Fallback", HttpStatus.SERVICE_UNAVAILABLE);
	}
	
	@GetMapping("/employee")
	public ResponseEntity<String> employeeFallback(){
		return new ResponseEntity<String>("Employee Service Unavailable", HttpStatus.SERVICE_UNAVAILABLE);
	}
}
