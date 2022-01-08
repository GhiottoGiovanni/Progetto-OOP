package com.twitter.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class Caller {
	public static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();
	private static final String[] USER_FIELDS = {"description", "location", "verified", "public_metrics"};
	
	public static String jsonTwitterUserDataFromUsername(String username) {
		String jsonData = jsonUserDataFromUsername(username, Caller.USER_FIELDS);		
		try {
			JsonNode jn = Caller.OBJECT_MAPPER.readTree(jsonData);
			return jn.at("/data").toString();
		} catch (JsonMappingException e) {
			e.printStackTrace();
			return "";
		} catch (JsonProcessingException e) {
			e.printStackTrace();
			return "";
		}
	}
	
	public static String followingDataFromUsername(String username, String nextToken) {
		int maxResults = 100;
		String endpoint = "following";
		return dataFromUsername(username, endpoint, maxResults, Caller.USER_FIELDS, nextToken);
	}
	
	private static String dataFromUsername(String username, String endpoint, int maxResults, String[] userFields, String nextToken) {
		String id = getIdFromUsername(username);
		String path = "https://api.twitter.com/2/users/" + id + "/" + endpoint;
		// adding the optional parameters
		path += "?max_results=" + maxResults;
		path += userFieldsToPath(userFields, false);
		if (nextToken != null && !nextToken.isBlank()) {
			path += "&pagination_token=" + nextToken; 
		}
		return jsonFromUrlString(path);
	}
	
	private static String jsonFromUrlString(String urlString) {
		try {
			URL url = new URL(urlString);
			HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
			// TODO get the bearer code from an xml file
			urlConnection.setRequestProperty("Authorization", "Bearer " + "AAAAAAAAAAAAAAAAAAAAAJjSWwEAAAAAdLSKaXAoGu%2BxVx5nO1W3dVxpdz0%3DQLVtGkBY0JOvBECcNk7o3ZVDyZJob1G6M7jHKP9xmupCYHOad3");
			urlConnection.setRequestMethod("GET");
			
			String line = "";
			InputStreamReader isr = new InputStreamReader(urlConnection.getInputStream());
			BufferedReader br = new BufferedReader(isr);
			StringBuilder sb = new StringBuilder();
			while ((line = br.readLine()) != null) {
				sb.append(line);
			}
			br.close();
			return sb.toString();
		} catch (MalformedURLException e) {
			e.printStackTrace();
			return "";
		} catch (ProtocolException e) {
			e.printStackTrace();
			return "";
		} catch (IOException e) {
			e.printStackTrace();
			return "";
		}
	}
	
	private static String jsonUserDataFromUsername(String username, String[] userFields) {
		return jsonFromUrlString("https://api.twitter.com/2/users/by/username/" + username + userFieldsToPath(userFields, true));
	}
	
	private static String getIdFromUsername(String username) {
		String jsonData = jsonUserDataFromUsername(username, null);		
		try {
			JsonNode jn = Caller.OBJECT_MAPPER.readTree(jsonData);
			return jn.at("/data/id").asText();
		} catch (JsonMappingException e) {
			e.printStackTrace();
			return "";
		} catch (JsonProcessingException e) {
			e.printStackTrace();
			return "";
		}
	}
	
	private static String userFieldsToPath(String[] userFields, boolean atTheBeginning) {
		String userFieldsUrlFormat = "";
		String specialCharacter = atTheBeginning ? "?" : "&";
		if (userFields != null && userFields.length != 0) {
			userFieldsUrlFormat += specialCharacter;
			userFieldsUrlFormat += "user.fields=";
			userFieldsUrlFormat += userFields[0];
			for (int i = 1; i < userFields.length; i++) {
				userFieldsUrlFormat += "," + userFields[i];
			}
		}
		return userFieldsUrlFormat;
	}
}
