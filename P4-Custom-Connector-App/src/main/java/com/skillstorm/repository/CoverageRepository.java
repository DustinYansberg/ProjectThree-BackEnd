package com.skillstorm.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.skillstorm.models.Coverage;
import com.skillstorm.models.Employee;

@Repository
public interface CoverageRepository extends CrudRepository<Coverage, Integer> {
	
    
    
}
