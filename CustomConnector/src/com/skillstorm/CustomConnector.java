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
	
	//	Match form elements in custom-connector-form
	private String hostUrl;
	private String hostUsername;
	private String hostPassword;
	private String authString;
	
	/**
	 * configure()
	 * Configuration function to instantiate variables at the beginning of operations.
	 */
	public void configure() {
		this.hostUrl = config.getConfig().get("hostUrl").toString();
		this.hostUsername = config.getConfig().get("hostUsername").toString();
		this.hostPassword = config.getConfig().get("hostPassword").toString();
		this.authString = "Basic " + Base64.getEncoder().encodeToString((hostUsername + ":" + hostPassword).getBytes());
	}

	/**
	 * createConnection()
	 * Modular connection creation function.
	 * @param rqUrl - URL to make request to.
	 * @param rqMethod - HttpMethod of request. (GET/POST/PUT/DELETE)
	 * @return HttpURLConnection to rqUrl of methof rqMethod
	 * @throws IOException
	 */
	private HttpURLConnection createConnection(String rqUrl, String rqMethod) throws IOException {
		URL url = new URL(hostUrl + rqUrl);
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		conn.setRequestMethod(rqMethod);
		conn.setRequestProperty("Authorization", authString);
		
		return conn;
	}
	
	/**
	 * testConnection()
	 * Tests connection from SailPoint to the custom application.
	 * If connection was not successful or if response code was not 200, will throw ConnectorException.
	 */
	@Override
	public void testConnection() throws ConnectorException{
		try {
			configure();
			
			//	Connect to the UserController test method.
			HttpURLConnection conn = createConnection("/identity/test", "GET");
			int responseCode = conn.getResponseCode();

			if(responseCode != 200) {
				throw new IOException("Response code was not 200, but was " + responseCode);
			}
		} catch(IOException e) {
			throw new ConnectorException(e.toString());
		}
	}
	
	/**
	 * iterate()
	 * Aggregates all accounts from the custom app and imports them into sailpoint. Called via SailPoint task.
	 */
	@Override
	public Iterator<Map<String, Object>> iterate(Filter arg0) throws ConnectorException, UnsupportedOperationException {
		try {
			configure();
			
			HttpURLConnection conn = createConnection("/identity", "GET");
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

	/**
	 * getConnectionResponse()
	 * Modular function for processing the response from an HttpURLConnection using a BufferedReader
	 * @param conn - The connection to get the response of.
	 * @return String version of a StringBuffer that collects all information from the input stream BufferedReader.
	 * @throws IOException
	 */
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

	/**
	 * read()
	 * Currently unimplemented function to get one account from the custom app.
	 * Unused since our custom app has no need to get one account from this custom app.
	 */
	@Override
	public Map<String, Object> read(String arg0)
			throws ConnectorException, ObjectNotFoundException, UnsupportedOperationException {
		// TODO Auto-generated method stub
		return null;
	}
	
	/**
	 * handleBodyRequest()
	 * Modularization of create() and update() functions, since their code and operations are largely the same.
	 * @param usernameAsId - The username of the custom app account to create/update, required for indexing purposes.
	 * @param items - The parameters present in the body of the request.
	 * @param isPUT - True if we are making a PUT request (update()), false otherwise (create()).
	 * @return Result of the operation.
	 */
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
			
			HttpURLConnection conn = createConnection("/identity", isPUT ? "PUT" : "POST");
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
	
	/**
	 * create()
	 * Creates a new user in the custom application.
	 * @return Result of the creation.
	 */
	public Result create(String usernameAsId, List<Item> items) {
		return handleBodyRequest(usernameAsId, items, false);
	}

	/**
	 * update()
	 * Updates an existing user in the custom application.
	 * @return Result of the update.
	 */
	public Result update(String usernameAsId, List<Item> items) {
		return handleBodyRequest(usernameAsId, items, true);
	}
}
