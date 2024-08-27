package com.skillstorm.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.skillstorm.models.Identity;
import com.skillstorm.services.IdentityService;

@RestController
@RequestMapping("/identity")
public class IdentityController {
	@Autowired private IdentityService service;
	
	//	Test connection method: CustomConnector testConnection() calls this endpoint
	@GetMapping("/test") public String testConnection() {
		System.out.println("Connection successful.");
		return "Connection successful.";
	}

	@GetMapping public Iterable<Identity> getAllIdentities() {
		return service.getAllIdentities();
	}
	
	@GetMapping("/{id}") public Identity getIdentityById(@PathVariable int id) {
		return service.getIdentityById(id);
	}
	
	@PostMapping public Identity createIdentity(@RequestBody Identity identity) {
		return service.createIdentity(identity);
	}
	
	@PutMapping("/{id}") public Identity updateIdentity(@PathVariable int id,
			@RequestBody Identity identity) {
		return service.updateIdentity(id, identity);
	}
	
	@DeleteMapping("/{id}") public Identity deleteIdentityById(@PathVariable int id) {
		return service.deleteIdentityById(id);
	}
}
