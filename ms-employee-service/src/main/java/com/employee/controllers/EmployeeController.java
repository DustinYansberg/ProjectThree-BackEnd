package com.employee.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.employee.models.EmployeeRequest;
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
	
	@PostMapping()
	public ResponseEntity<? extends Object> createEmployee(@RequestBody EmployeeRequest newEmployee) {
		return service.createEmployee(newEmployee);
	}
	
	//	TODO There was an issue in our project where trying to update an employee through SCIM API
	//		would result in unspecified values set to null.
	@PutMapping("/{id}")
	public ResponseEntity<? extends Object> updateEmployeeById(@PathVariable String id,
															@RequestBody EmployeeRequest newDetails) {
		return null;
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<? extends Object> deleteEmployeeById(@PathVariable String id) {
		return service.deleteEmployeeById(id);
	}
}
