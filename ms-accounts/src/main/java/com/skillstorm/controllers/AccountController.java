package com.skillstorm.controllers;

import java.util.ArrayList;
import java.util.Base64;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.skillstorm.Config.RabbitMQConfig;
import com.skillstorm.models.PermissionUpdateMessage;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.skillstorm.models.Account;
import com.skillstorm.models.NewAccount;

@RestController
@RequestMapping("/account")
@CrossOrigin(origins = "*")
public class AccountController {
	@Value("${spring.datasource.username}") private String username;
	@Value("${spring.datasource.password}") private String password;
	@Value("${spring.datasource.url}/account") private String pluginUrl;
	@Value("${spring.datasource.scimUrl}/Accounts/") private String scimUrl;
	

	private HttpHeaders generateAuthHeaders() {
		HttpHeaders headers = new HttpHeaders();
		headers.add("Authorization", "Basic " + Base64.getEncoder().encodeToString((username + ":" + password).getBytes()));
		return headers;
	}
	private ResponseEntity<Object> processError(Exception e) {
		if(!e.toString().contains("detail")) {
			return ResponseEntity.status(201).header("Success", "Account successfully created")
					.body(null);
		}
		else {
			return ResponseEntity.status(500).header("Error", "SCIM Error " + e)
					.body("An error occurred when sending the request to SCIM:\n" + e);
		}
	}
	private ResponseEntity<Object> sendRestTemplateExchange(Object body, String url, HttpMethod method) {
		RestTemplate temp = new RestTemplate();
		HttpEntity<Object> entity = new HttpEntity<>(body, generateAuthHeaders());
		return temp.exchange(url, method, entity, Object.class);
	}
	
	@GetMapping
	public ResponseEntity<Object> test(){
		try {
			return sendRestTemplateExchange(null, pluginUrl, HttpMethod.GET);
		}catch (Exception e) {
			return processError(e);
		}
	}
	
	@GetMapping("/identity/{id}")
	public ResponseEntity<Object> getAccountsByIdentity(@PathVariable String id){
		try {
			return sendRestTemplateExchange(null, pluginUrl + "/byIdentity/" + id, HttpMethod.GET);
		} catch(Exception e) {
			return processError(e);
		}
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Object> getAccountById(@PathVariable String id) {
		try {
			return sendRestTemplateExchange(null, pluginUrl + "/get/" + id, HttpMethod.GET);
		} catch(Exception e) {
			return processError(e);
		}
	}
	
	@PostMapping
	public ResponseEntity<Object> createAccount(@RequestBody NewAccount newAccount){
		try {
			return sendRestTemplateExchange(newAccount.toJsonString(), scimUrl, HttpMethod.POST);
		} catch(Exception e) {
			return processError(e);
		}
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<Object> updateAccount(@PathVariable String id, @RequestBody Account body) throws JsonProcessingException{

		try {
			Map<String, Object> info =  (Map<String, Object>) sendRestTemplateExchange(null, pluginUrl + "/getUpdate/" + id, HttpMethod.GET).getBody();
			return sendRestTemplateExchange(
					body.toJsonString(info), 
					scimUrl + id, 
					HttpMethod.PUT);
		} catch(Exception e) {
			return processError(e);
		}
	}

	@RabbitListener(queues = RabbitMQConfig.QUEUE_NAME)
	public void updatePermission(PermissionUpdateMessage message) throws JsonProcessingException{
		String accountId = this.getFirstAccountByIdentityId(message.getId());
		try {
			Map<String, Object> info =  (Map<String, Object>) sendRestTemplateExchange(null, pluginUrl + "/getUpdate/" + accountId, HttpMethod.GET).getBody();
			Map<String, Object> map = (Map<String, Object>) sendRestTemplateExchange(null, pluginUrl + "/get/" + accountId, HttpMethod.GET).getBody();
			ObjectMapper mapper = new ObjectMapper();
			Account account = mapper.convertValue(map, Account.class);
			System.out.println(account.toJsonStringWithPermissions(info, message.getPermission()));
			 sendRestTemplateExchange(
					account.toJsonStringWithPermissions(info, message.getPermission()),
					scimUrl + accountId,
					HttpMethod.PUT);
		} catch(Exception e) {
			System.out.println(processError(e));
		}
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Object> deleteAccountById(@PathVariable String id) {
		try {
			return sendRestTemplateExchange(null, scimUrl  + id, HttpMethod.DELETE);
		} catch(Exception e) {
			return processError(e);
		}
	}
	
	private String getFirstAccountByIdentityId(String id) {
		try {
			System.out.println(sendRestTemplateExchange(null, pluginUrl + "/byIdentity/" + id, HttpMethod.GET).getBody());
			List<Map<String, Object>> test = (List<Map<String, Object>>) sendRestTemplateExchange(null, pluginUrl + "/byIdentity/" + id, HttpMethod.GET).getBody();
			System.out.println(test);
			Object result = test.get(0).get("accountId");
			System.out.println(result);
			return result.toString();
		} catch(Exception e) {
			return processError(e).toString();
		}
	}
}
