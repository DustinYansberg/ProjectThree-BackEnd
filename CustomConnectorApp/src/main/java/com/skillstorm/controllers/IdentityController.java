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
    @Autowired
    private IdentityService service;

    /**
     * testConnection()
     * Test connection method: CustomConnector testConnection() calls this endpoint
     * @return "Connection successful" if SailPoint was able to successfully connect to the app.
     */
    @GetMapping("/test")
    public String testConnection() {
        System.out.println("Connection successful.");
        return "Connection successful.";
    }

    /**
     * getAllIdentities()
     * Basic getAll function for Identities.
     * @return A list of Identities in the app.
     */
    @GetMapping
    public Iterable<Identity> getAllIdentities() {
	    return service.getAllIdentities();
    }

    /**
     * getIdentityById()
     * A function to get an identity using its ID as a reference.
     * @param id - ID of the Identity to get.
     * @return Information about the Identity with ID id.
     */
    @GetMapping("/{id}")
    public Identity getIdentityById(@PathVariable int id) {
	    return service.getIdentityById(id);
    }

    /**
     * createIdentity()
     * A function to create a new Identity given a set of values.
     * @param identity - Set of values for new Identity.
     * @return Information about the created Identity.
     */
    @PostMapping
    public Identity createIdentity(@RequestBody Identity identity) {
	    return service.createIdentity(identity);
    }

    /**
     * updateIdentity()
     * A function to update an existing Identity with a set of new data.
     * @param id - Required ID of entity to update.
     * @param identity - Information to update the entity with.
     * @return Information about the updated Identity.
     */
    @PutMapping("/{id}")
    public Identity updateIdentity(@PathVariable int id, @RequestBody Identity identity) {
    	return service.updateIdentity(id, identity);
    }

    /**
     * deleteIdentityById()
     * A function to delete an existing Identity by its ID.
     * @param id - ID of identity to delete.
     * @return Information about the deleted Identity.
     */
    @DeleteMapping("/{id}")
    public Identity deleteIdentityById(@PathVariable int id) {
    	return service.deleteIdentityById(id);
    }
}
