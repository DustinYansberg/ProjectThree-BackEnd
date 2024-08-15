package com.employee.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.employee.services.EmployeeService;

@RestController
@RequestMapping("/employee")
public class EmployeeController {
	private final EmployeeService service;
	
	public EmployeeController(EmployeeService service) {
		this.service = service;
	}
	
	@GetMapping
	public ResponseEntity<? extends Object> getAllEmployees() {
		return service.getAllEmployees();
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<? extends Object> getEmployeeById(@PathVariable String id) {
		return service.getEmployeeById(id);
	}
}
