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

	private HttpURLConnection createConnection(String rqUrl, String rqMethod) throws IOException {
		URL url = new URL(hostUrl + rqUrl);
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		conn.setRequestMethod(rqMethod);
		conn.setRequestProperty("Authorization", authString);
		
		return conn;
	}
	
	@Override
	public void testConnection() throws ConnectorException{
		try {
			configure();
			
			//	Connect to the UserController test method.
			HttpURLConnection conn = createConnection("/employee/test", "GET");
			int responseCode = conn.getResponseCode();

			if(responseCode != 200) {
				throw new IOException("Response code was not 200, but was " + responseCode);
			}
		} catch(IOException e) {
			throw new ConnectorException(e.toString());
		}
	}
	
	@Override
	public Iterator<Map<String, Object>> iterate(Filter arg0) throws ConnectorException, UnsupportedOperationException {
		try {
			configure();
			
			HttpURLConnection conn = createConnection("/employee", "GET");
			int responseCode = conn.getResponseCode();
			if(responseCode != 200) {
				throw new IOException("Response code was not 200, but was " + responseCode + ": " + conn.getContent().toString());
			}
			else {
				String response = getConnectionResponse(conn);
				ObjectMapper mapper = new ObjectMapper();
				List<Map<String, Object>> map = mapper.readValue(response, new TypeReference<List<Map<String, Object>>>(){});
				Iterator<Map<String, Object>> iterator = map.iterator();
				return iterator;
			}
		} catch(IOException e) {
			throw new ConnectorException(e.toString());
		}
	}

	private String getConnectionResponse(HttpURLConnection conn) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
		StringBuffer sb = new StringBuffer();
		String currentLine = br.readLine();
		while(currentLine != null) {
			sb.append(currentLine);
			currentLine = br.readLine();
		}
		br.close();
		return sb.toString();
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
			
			HttpURLConnection conn = createConnection("/employee", isPUT ? "PUT" : "POST");
			conn.setRequestProperty("Content-Type", "application/json");
			conn.setRequestProperty("Accept", "application/json");
			conn.setRequestProperty("Content-Length", String.valueOf(bytes.length));
			conn.setDoOutput(true);
			conn.getOutputStream().write(bytes);
			String respStr = getConnectionResponse(conn);
			
			ObjectMapper mapper = new ObjectMapper();
			response = mapper.readValue(respStr, new TypeReference<Map<String, Object>>(){});
		} catch(IOException e) {
			throw new ConnectorException(e.getMessage());
		}
		
		Result result = new Result();
		result.setObject(response);
		return result;
	}
	
	//	TODO Test these two
	public Result create(String usernameAsId, List<Item> items) {
		return handleBodyRequest(usernameAsId, items, false);
	}

	public Result update(String usernameAsId, List<Item> items) {
		return handleBodyRequest(usernameAsId, items, true);
	}
}
