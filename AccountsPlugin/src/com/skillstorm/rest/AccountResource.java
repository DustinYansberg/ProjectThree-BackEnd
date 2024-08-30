package com.skillstorm.rest;


import java.util.List;
import java.util.Map;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.skillstorm.services.AccountService;

import sailpoint.rest.plugin.AllowAll;
import sailpoint.rest.plugin.BasePluginResource;
import sailpoint.tools.GeneralException;

@Path("account")
public class AccountResource extends BasePluginResource {

	public AccountService service() {
		return new AccountService(this);
	}
	@Override
	public String getPluginName() {
		return "Accounts Plugin";
	}

	//GET method for returning an Account by it's ID
	@GET
	@Path("get/{id}")
	@AllowAll
	@Produces(MediaType.APPLICATION_JSON)
	public Map<String, Object> getById(@PathParam(value="id") String id) throws GeneralException{
		return service().getAccountById(id);
	}
	
	//GET method for returning a list of Accounts associated with an Identity
	@GET
	@Path("byIdentity/{id}")
	@AllowAll
	@Produces(MediaType.APPLICATION_JSON)
	public List<Map<String, Object>> getByIdentityId(@PathParam(value="id") String id) throws GeneralException{
		return service().getAccountsByIdentityId(id);
	}
	
	//GET method used to assist in filling out information for updating Accounts
	@GET
	@Path("getUpdate/{id}")
	@AllowAll
	@Produces(MediaType.APPLICATION_JSON)
	public Map<String, Object> getUpdateInfo(@PathParam(value="id") String id) throws GeneralException{
		return service().getUpdateInfo(id);
	}
	
	//Method to test plugin connection 
	@GET
	@AllowAll
	@Produces(MediaType.APPLICATION_JSON)
	public String test() {
		return "Connection successful";
	}
	
}
