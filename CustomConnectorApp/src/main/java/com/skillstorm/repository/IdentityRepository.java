package com.skillstorm.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.skillstorm.models.Identity;

@Repository
public interface IdentityRepository extends CrudRepository<Identity, Integer> {
	
}
