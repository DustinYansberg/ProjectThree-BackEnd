package com.skillstorm;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Base64;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import openconnector.AbstractConnector;
import openconnector.ConnectorException;
import openconnector.Filter;
import openconnector.Item;
import openconnector.ObjectNotFoundException;
import openconnector.Result;

public class CustomConnector extends AbstractConnector {
	
	//	Match form elements in customm-connector-form
	private String hostUrl;
	private String hostUsername;
	private String hostPassword;
	private String authString;
	
	public void configure() {
		this.hostUrl = config.getConfig().get("hostUrl").toString();
		this.hostUsername = config.getConfig().get("hostUsername").toString();
		this.hostPassword = config.getConfig().get("hostPassword").toString();
		this.authString = "Basic " + Base64.getEncoder().encodeToString((hostUsername + ":" + hostPassword).getBytes());
	}

	@Override
	public void testConnection() throws ConnectorException{
		try {
			configure();
			
			//	Create a GET Request to custom app
			URL url = new URL(hostUrl + "/employee/test");
	        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
	        conn.setRequestMethod("GET");
	        conn.setRequestProperty("Authorization", authString);
			int responseCode = conn.getResponseCode();

			// Check if response was 200, throw IOException if not
			if(responseCode != 200) {
				throw new IOException("Response failed: " + responseCode);
			}
		} catch(IOException e) {
			throw new ConnectorException(e.toString());
		}
	}
	
	@Override
	public Iterator<Map<String, Object>> iterate(Filter arg0) throws ConnectorException, UnsupportedOperationException {
		try {
			configure();
			
			// GET request to custom app
			URL url = new URL(hostUrl + "/employee");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Authorization", authString);
            int responseCode = conn.getResponseCode();
            
            // Throw IOException if unsuccessful response
			if(responseCode != 200) {
				throw new IOException(responseCode + ": " + conn.getContent().toString());
			}
			else {
			    BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
		        StringBuffer sb = new StringBuffer();
		        String currentLine = br.readLine();
		        while(currentLine != null) {
		            sb.append(currentLine);
		            currentLine = br.readLine();
		        }
		        br.close();
				String response = sb.toString();
				ObjectMapper mapper = new ObjectMapper();
				
				// Map http response to List
				List<Map<String, Object>> map = mapper.readValue(response, new TypeReference<List<Map<String, Object>>>(){});
				
				// Create an iterator for IIQ and return it
				Iterator<Map<String, Object>> iterator = map.iterator();
				return iterator;
			}
		} catch(IOException e) {
			throw new ConnectorException(e.toString());
		}
	}

	@Override
	public Map<String, Object> read(String arg0)
			throws ConnectorException, ObjectNotFoundException, UnsupportedOperationException {
		// TODO Auto-generated method stub
		return null;
	}
	
	//	Modularization: create() and update() are largely the same
	private Result handleBodyRequest(String usernameAsId, List<Item> items, boolean isPUT) {
		System.out.println("A: usernameAsId = " + usernameAsId);
		for(Item i : items) {System.out.println("B: " + i.getName() + " - " + i.getValue());}
		
		//	create the payload to send
		Map<String, Object> payload = new HashMap<>();
		for(Item i : items) {
			payload.put(i.getName(), i.getValue());
		}
		payload.put("username", usernameAsId);
		Map<String, Object> response = new HashMap<>();
		
		//	request
		try {
			configure();
			byte[] bytes = new ObjectMapper().writeValueAsString(payload).getBytes();
			
			URL url = new URL(hostUrl + "/employee");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod(isPUT ? "PUT" : "POST");
            conn.setRequestProperty("Authorization", authString);
			conn.setRequestProperty("Content-Type", "application/json");
			conn.setRequestProperty("Accept", "application/json");
			conn.setRequestProperty("Content-Length", String.valueOf(bytes.length));
			conn.setDoOutput(true);
			conn.getOutputStream().write(bytes);
			
			
			BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            StringBuffer sb = new StringBuffer();
            String currentLine = br.readLine();
            while(currentLine != null) {
                sb.append(currentLine);
                currentLine = br.readLine();
            }
            br.close();
            String respStr = sb.toString();

			ObjectMapper mapper = new ObjectMapper();
			response = mapper.readValue(respStr, new TypeReference<Map<String, Object>>(){});
		} catch(IOException e) {
			throw new ConnectorException(e.getMessage());
		}
		
		Result result = new Result();
		result.setObject(response);
		return result;
	}
	
	public Result create(String usernameAsId, List<Item> items) {
		return handleBodyRequest(usernameAsId, items, false);
	}

	public Result update(String usernameAsId, List<Item> items) {
		return handleBodyRequest(usernameAsId, items, true);
	}
}
