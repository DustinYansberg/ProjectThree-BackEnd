package com.skillstorm.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.skillstorm.models.Employee;
import com.skillstorm.repository.EmployeeRepository;

@Service
public class EmployeeService {
	@Autowired private EmployeeRepository repo;
	
	@Autowired
    private PasswordEncoder passwordEncoder;
	
	public Iterable<Employee> getAllEmployees() {
		return repo.findAll();
	}
	
	public Employee getEmployeeById(int id) {
		return repo.findById(id).get();
	}
	
	public Employee createEmployee(Employee employee) {
	    String encodedPassword = passwordEncoder.encode(employee.getPassword());
        employee.setPassword(encodedPassword); // Set the encoded password
		return repo.save(employee);
	}
	
	public Employee updateEmployee(String username, Employee newId) {
		newId.setUsername(username);
		return repo.save(newId);
	}
	
	public Employee deleteEmployeeById(int id) {
		Employee deleted = getEmployeeById(id);
		if(deleted == null) {
			return null;
		}
		else {
			repo.deleteById(id);
			return deleted;
		}
	}
}
