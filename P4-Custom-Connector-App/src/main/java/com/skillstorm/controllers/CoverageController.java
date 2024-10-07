package com.skillstorm.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.skillstorm.models.Coverage;
import com.skillstorm.models.Employee;
import com.skillstorm.services.CoverageService;
import com.skillstorm.services.EmployeeService;

@RestController
@RequestMapping("/coverage")
@CrossOrigin("*")
public class CoverageController {
    @Autowired
    private CoverageService service;

    // Test connection method: CustomConnector testConnection() calls this endpoint
    @GetMapping("/test")
    public String testConnection() {
	System.out.println("Connection successful.");
	return "Connection successful.";
    }

    @GetMapping
    public Iterable<Coverage> getAllCoverage() {
	return service.getAllCoverage();
    }

    @GetMapping("/{id}")
    public Coverage getCoverageById(@PathVariable int id) {
	return service.getCoverageById(id);
    }

    @PostMapping
    public Coverage createCoverage(@RequestBody Coverage coverage) {
	return service.createCoverage(coverage);
    }

    @PutMapping("/{id}")
    public Coverage updateCoverage(@PathVariable int id, @RequestBody Coverage coverage) {
	return service.updateCoverage(id, coverage);
    }

    @DeleteMapping("/{id}")
    public Coverage deleteCoverageById(@PathVariable int id) {
	return service.deleteCoverageById(id);
    }
}
