package com.skillstorm.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.skillstorm.models.Identity;
import com.skillstorm.repository.IdentityRepository;

@Service
public class IdentityService {
	@Autowired private IdentityRepository repo;
	
	/**
     * getAllIdentities()
     * Basic getAll function for Identities.
     * @return A list of Identities in the app.
     */
	public Iterable<Identity> getAllIdentities() {
		return repo.findAll();
	}
	
	/**
     * getIdentityById()
     * A function to get an identity using its ID as a reference.
     * @param id - ID of the Identity to get.
     * @return Information about the Identity with ID id.
     */
	public Identity getIdentityById(int id) {
		return repo.findById(id).get();
	}
	
	/**
     * createIdentity()
     * A function to create a new Identity given a set of values.
     * @param identity - Set of values for new Identity.
     * @return Information about the created Identity.
     */
	public Identity createIdentity(Identity id) {
		return repo.save(id);
	}
	
	/**
     * updateIdentity()
     * A function to update an existing Identity with a set of new data.
     * @param id - Required ID of entity to update.
     * @param identity - Information to update the entity with.
     * @return Information about the updated Identity.
     */
	public Identity updateIdentity(int id, Identity newId) {
		newId.setId(id);
		return repo.save(newId);
	}
	
	/**
     * deleteIdentityById()
     * A function to delete an existing Identity by its ID.
     * @param id - ID of identity to delete.
     * @return Information about the deleted Identity.
     */
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
