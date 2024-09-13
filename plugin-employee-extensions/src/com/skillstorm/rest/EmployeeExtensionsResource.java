package com.skillstorm.rest;

import java.util.ArrayList;
import java.util.Map;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.joda.time.LocalDateTime;

import com.skillstorm.models.PwdRequest;
import com.skillstorm.services.EmployeeExtensionsService;

import sailpoint.rest.plugin.AllowAll;
import sailpoint.rest.plugin.BasePluginResource;
import sailpoint.tools.GeneralException;

@Path("EmployeeExtensionsPlugin")
public class EmployeeExtensionsResource extends BasePluginResource {

	@Override
	public String getPluginName() {return "EmployeeExtensionsPlugin";}
	
	private EmployeeExtensionsService service() {
//		return new EmployeeExtensionsService(this); gives me an error for some reason saying the constructor is undefined
		return new EmployeeExtensionsService(null);
	}
    
	@GET
	@Path("getAll")
	@AllowAll
	@Produces(MediaType.APPLICATION_JSON)
	public ArrayList<Map<String, Object>> getAll() throws GeneralException {
		return service().getAll();
	}
	
	private String testBlock(String methodName, String payload) {
		try {
			String returnStr = LocalDateTime.now() + " | " + methodName + " succeeded: " + payload;
			System.out.println(returnStr);
			return returnStr;
		} catch(Exception e) {
			return "An error occurred with " + methodName + " (" + payload + "): \n" + e;
		}
	}
	
	@PUT
	@Path("test1")
	@AllowAll
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public String testInvalidMethod(String testStr) throws GeneralException {
		return testBlock("Test 1 - Basic Function", testStr);
	}
	
	//	Does not even INSTALL correctly because we cannot have multiple parameters in a plugin method
	//		unless they have annotations.
//	@PUT
//	@Path("test2")
//	@AllowAll
//	@Consumes(MediaType.APPLICATION_JSON)
//	@Produces(MediaType.APPLICATION_JSON)
//	public String testInvalidMethodMultParams(String testStr1, String testStr2) throws GeneralException {
//		return testBlock("Test 2 - Multiple parameters", testStr1 + " | " + testStr2);
//	}

	@PUT
	@Path("test3")
	@AllowAll
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public String testInvalidMethodMapParams(Map<String, Object> testParams) throws GeneralException {
		return testBlock("Test 3 - Map Params", testParams.toString());
	}
	
	@PUT
	@Path("test4/{id}")
	@AllowAll
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public String testInvalidMethodPathParams(@PathParam("id") String id, Map<String, Object> testParams) throws GeneralException {
		return testBlock("Test 4 - Path Params", id + " | " + testParams.toString());
	}
	
	@PUT
	@Path("pwdUpdate/{id}")
	@AllowAll
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public String updatePasswordDirectly(@PathParam("id") String id, Map<String, String> parameters) throws GeneralException {
		System.out.println(LocalDateTime.now() + ": pwdUpdate running... ");
    	return service().updatePasswordDirectly(id, parameters.get("oldPwd"), parameters.get("newPwd")); 
    }
}
