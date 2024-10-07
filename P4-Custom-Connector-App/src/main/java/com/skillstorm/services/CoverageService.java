package com.skillstorm.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.skillstorm.models.Coverage;
import com.skillstorm.models.Employee;
import com.skillstorm.repository.CoverageRepository;
import com.skillstorm.repository.EmployeeRepository;

@Service
public class CoverageService {
	@Autowired private CoverageRepository repo;
	
	public Iterable<Coverage> getAllCoverage() {
		return repo.findAll();
	}
	
	public Coverage getCoverageById(int id) {
		return repo.findById(id).get();
	}
	
	public Coverage createCoverage(Coverage coverage) {
		return repo.save(coverage);
	}
	
	public Coverage updateCoverage(int id, Coverage newId) {
		newId.setId(id);
		return repo.save(newId);
	}
	
	public Coverage deleteCoverageById(int id) {
		Coverage deleted = getCoverageById(id);
		if(deleted == null) {
			return null;
		}
		else {
			repo.deleteById(id);
			return deleted;
		}
	}
}
