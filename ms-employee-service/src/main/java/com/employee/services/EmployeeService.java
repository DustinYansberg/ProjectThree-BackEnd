package com.employee.services;

import java.util.ArrayList;
import java.util.Base64;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.employee.models.EmployeeRequest;
import com.employee.models.EmployeeResponse;
import com.employee.models.SCIMResponseObject;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

@Service
public class EmployeeService {
    @Value("${spring.datasource.url}/Users")
    private String baseUrl;
    @Value("${spring.datasource.username}")
    private String username;
    @Value("${spring.datasource.password}")
    private String password;

    ObjectMapper mapper = new ObjectMapper().enable(SerializationFeature.INDENT_OUTPUT);

    /**
     * generateAuthHeaders() A function for generating required SailPoint
     * authorization headers for operations.
     *
     * @return An HttpHeaders object with encoded Basic Auth headers.
     */
    private HttpHeaders generateAuthHeaders() {
	HttpHeaders headers = new HttpHeaders();
	headers.add("Authorization",
		"Basic " + Base64.getEncoder().encodeToString((username + ":" + password).getBytes()));
	return headers;
    }

    /**
     * sendRestTemplateExchange() A function to pass various types of RestTemplate
     * exchanges without having to retype code.
     *
     * @param body   - The object to send. For GET and DELETE, is null.
     * @param url    - The url to send to.
     * @param method - The CRUD operation to perform, as an HttpMethod.
     * @return A ResponseEntity containing the results of the exchange.
     */
    private ResponseEntity<Object> sendRestTemplateExchange(Object body, String url, HttpMethod method) {
	RestTemplate temp = new RestTemplate();
	HttpEntity<Object> entity = new HttpEntity<>(body, generateAuthHeaders());
	return temp.exchange(url, method, entity, Object.class);
    }
    
    /**
     * sendRestTemplateExchange() A function to pass various types of RestTemplate
     * exchanges without having to retype code.
     *
     * @param body   - The object to send. For GET and DELETE, is null.
     * @param url    - The url to send to.
     * @param method - The CRUD operation to perform, as an HttpMethod.
     * @return A ResponseEntity containing the results of the exchange.
     */
    private ResponseEntity<Object> sendRestTemplateExchangeWithPagination(Object body, String url, HttpMethod method, int index, int row) {
	RestTemplate temp = new RestTemplate();
	HttpEntity<Object> entity = new HttpEntity<>(body, generateAuthHeaders());
	return temp.exchange(url + "?startIndex=" + index + "&count=" + row, method, entity, Object.class);
    }

    /**
     * processError() A function to process and display SCIM-related errors with
     * further details.
     *
     * @param e - The exception to process.
     * @return A ResponseEntity containing details about the exception.
     */
    private ResponseEntity<String> processError(Exception e) {
	e.printStackTrace();
	// TODO There is probably a way to formally extract error code from exception
	// message
	return ResponseEntity.status(500).header("Error", "SCIM Error")
		.body("An error occurred when sending the request to SCIM:\n" + e);
    }

    /**
     *
     * @return
     */
    public ResponseEntity<? extends Object> getAllEmployees() {
	try {
	    ResponseEntity<Object> resp = sendRestTemplateExchange(null, baseUrl, HttpMethod.GET);
	    SCIMResponseObject empl = mapper.readValue(mapper.writeValueAsString(resp.getBody()),
		    SCIMResponseObject.class);

	    return ResponseEntity.status(200).body(empl);
	} catch (Exception e) {
	    return processError(e);
	}
    }
    
    public ResponseEntity<? extends Object> getAllEmployeesWithPagination(int index, int row) {
    	try {
    	    ResponseEntity<Object> resp = sendRestTemplateExchangeWithPagination(null, baseUrl, HttpMethod.GET, index, row);
    	    SCIMResponseObject empl = mapper.readValue(mapper.writeValueAsString(resp.getBody()),
    		    SCIMResponseObject.class);

    	    return ResponseEntity.status(200).body(empl);
    	} catch (Exception e) {
    	    return processError(e);
    	}
        }

    /**
     *
     * @param id
     * @return
     */
    public ResponseEntity<? extends Object> getEmployeeById(String id) {
	try {
	    ResponseEntity<Object> resp = sendRestTemplateExchange(null, baseUrl + "/" + id, HttpMethod.GET);
	    return ResponseEntity.status(200)
		    .body(mapper.readValue(mapper.writeValueAsString(resp.getBody()), EmployeeResponse.class));
	} catch (Exception e) {
	    return processError(e);
	}
    }

    public ResponseEntity<? extends Object> getEmployeeByEmail(String email) {
	try {
	    ResponseEntity<Object> resp = sendRestTemplateExchange(null,
		    baseUrl + "?filter=emails eq \"" + email + "\"", HttpMethod.GET);

	    SCIMResponseObject empl = mapper.readValue(mapper.writeValueAsString(resp.getBody()),
		    SCIMResponseObject.class);

	    return ResponseEntity.status(200).body(empl.getResources()[0]);
	} catch (Exception e) {
	    return processError(e);
	}
    }

    /**
     * TODO Replace with SCIM filtering.
     *
     * @param managerId - ID of manager.
     * @return
     */
    public ResponseEntity<? extends Object> getEmployeesByManagerId(String managerId) {
	try {
//	    EmployeeResponse[] allEmployees = (EmployeeResponse[]) getAllEmployees().getBody();
//
//	    ArrayList<EmployeeResponse> employees = new ArrayList<>();
//	    for (EmployeeResponse e : allEmployees) {
//		if (e.getManager().containsKey("value")) {
//		    if (e.getManager().get("value").equals(managerId)) {
//			employees.add(e);
//		    }
//		}
//	    }
//	    return ResponseEntity.status(200).body(employees.toArray());
		
		ResponseEntity<Object> resp = sendRestTemplateExchange(null,
			    baseUrl + "?filter=urn:ietf:params:scim:schemas:extension:enterprise:2.0:User:manager.value eq \"" + managerId + "\"", HttpMethod.GET);

		    SCIMResponseObject empl = mapper.readValue(mapper.writeValueAsString(resp.getBody()),
			    SCIMResponseObject.class);

		    return ResponseEntity.status(200).body(empl.getResources()[0]);
	} catch (Exception e) {
	    return processError(e);
	}
    }

    /**
     *
     * @param newEmployee
     * @return
     */
    public ResponseEntity<? extends Object> createEmployee(EmployeeRequest newEmployee) {
	try {
	    ResponseEntity<Object> resp = sendRestTemplateExchange(newEmployee.toJsonString(), baseUrl,
		    HttpMethod.POST);
	    return ResponseEntity.status(201)
		    .body(mapper.readValue(mapper.writeValueAsString(resp.getBody()), EmployeeResponse.class));
	} catch (Exception e) {
	    return processError(e);
	}
    }

    // TODO
    public ResponseEntity<? extends Object> updateEmployee(String id, EmployeeRequest newFields) {
	try {
	    // Get data of existing object
	    ResponseEntity<Object> getResp = sendRestTemplateExchange(null, baseUrl + "/" + id, HttpMethod.GET);
	    EmployeeRequest employee = new EmployeeRequest(
		    mapper.readValue(mapper.writeValueAsString(getResp.getBody()), EmployeeResponse.class));

	    // Aggregate the data into the Update request made
	    employee.updateFields(newFields);
	    // Send the update request that now includes existing fields
	    System.out.println(employee.toJsonString());
	    ResponseEntity<Object> resp = sendRestTemplateExchange(employee.toJsonString(), baseUrl + "/" + id,
		    HttpMethod.PUT);
	    return ResponseEntity.status(201)
		    .body(mapper.readValue(mapper.writeValueAsString(resp.getBody()), EmployeeResponse.class));
	} catch (Exception e) {
	    return processError(e);
	}
    }

    /**
     *
     * @param id
     * @return
     */
    public ResponseEntity<? extends Object> deleteEmployeeById(String id) {
	try {
	    ResponseEntity<? extends Object> resp = getEmployeeById(id);
	    sendRestTemplateExchange(null, baseUrl + "/" + id, HttpMethod.DELETE);
	    return resp;
	} catch (Exception e) {
	    return processError(e);
	}
    }

}
