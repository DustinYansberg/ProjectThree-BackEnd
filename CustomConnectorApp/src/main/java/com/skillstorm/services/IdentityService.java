package com.skillstorm.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.skillstorm.models.Identity;
import com.skillstorm.repository.IdentityRepository;

@Service
public class IdentityService {
	@Autowired private IdentityRepository repo;
	
	public Iterable<Identity> getAllIdentities() {
		return repo.findAll();
	}
	
	public Identity getIdentityById(int id) {
		return repo.findById(id).get();
	}
	
	public Identity createIdentity(Identity id) {
		return repo.save(id);
	}
	
	public Identity updateIdentity(int id, Identity newId) {
		newId.setId(id);
		return repo.save(newId);
	}
	
	public Identity deleteIdentityById(int id) {
		Identity deleted = getIdentityById(id);
		if(deleted == null) {
			return null;
		}
		else {
			repo.deleteById(id);
			return deleted;
		}
	}
}
