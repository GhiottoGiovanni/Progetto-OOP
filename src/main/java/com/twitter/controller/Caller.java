package com.twitter.controller;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.Scanner;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Classe che gestisce le chiamate all'API di Twitter.
 * @author Giovanni Ghiotto
 * @author Mihail Bobeica
 * @version 1.0
 */

public class Caller {
	/**
	 * Oggetto che fornisce le funzionalità per leggere e scrivere il Json.
	 */
	public static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();
	
	/**
	 * Campi prelevati dal profilo dell'utente.
	 */
	private static final String[] USER_FIELDS = {"description", "location", "verified", "public_metrics"};
	
	/**
	 * Nome completo del file contenente il bearer token (chiave di autenticazione).
	 */
	private static final String BEARER_FILENAME = "./src/main/resources/key/bearer.txt";
	
	/**
	 * Bearer token (chiave di autenticazione).
	 */
	private static final String BEARER = getBearer();
	
	/**
	 * @return Preleva da file locale il bearer token.
	 */
	private static String getBearer() {
		try {
			File bearerFile = new File(BEARER_FILENAME);
		    Scanner scanner = new Scanner(bearerFile);
		    String bearer = scanner.nextLine();
		    scanner.close();
		    return bearer;
		} catch (FileNotFoundException e) {
			// TODO print some message
			System.out.println("MESSAGGIO ERRORE: Problema con l'ottenimento del Bearer Token");
		    e.printStackTrace();
		    return null;
		}
	}
	
	/**
	 * <b>Metodo</b> che preleva i dati di un utente dall'API Twitter.
	 * @param username Nome identificativo dell'account Twitter.
	 * @return Dati dell'utente in formato stringa Json.
	 */
	public static String jsonTwitterUserDataFromUsername(String username) {
		String jsonData = jsonUserDataFromUsername(username, Caller.USER_FIELDS);	
		if (isNotNullOrEmptyString(jsonData)) {
			try {
				JsonNode jn = Caller.OBJECT_MAPPER.readTree(jsonData);
				return jn.at("/data").toString();
			} catch (JsonMappingException e) {
				// TODO Auto-generated catch block
				// e.printStackTrace();
				return null;
			} catch (JsonProcessingException e) {
				// TODO Auto-generated catch block
				// e.printStackTrace();
				return null;
			}
		} else {
			return null;
		}
	}
	
	/**
	 * <b>Metodo</b> che preleva i dati degli amici di un utente dall'API Twitter.
	 * @param username Nome identificativo dell'account Twitter.
	 * @param nextToken Token identificativo per i risultati della pagina successiva.
	 * @return Dati degli amici dell'utente in formato stringa Json.
	 */
	public static String followingDataFromUsername(String username, String nextToken) {
		int maxResults = 100;
		String endpoint = "following";
		return dataFromUsername(username, endpoint, maxResults, Caller.USER_FIELDS, nextToken);
		// returns null if bad request
	}
	
	private static String dataFromUsername(String username, String endpoint, int maxResults, String[] userFields, String nextToken) {
		String id = getIdFromUsername(username);
		if (isNotNullOrEmptyString(id)) {
			String path = "https://api.twitter.com/2/users/" + id + "/" + endpoint;
			// adding the optional parameters
			path += "?max_results=" + maxResults;
			path += userFieldsToPath(userFields, false);
			if (nextToken != null && !nextToken.isBlank()) {
				path += "&pagination_token=" + nextToken; 
			}
			return jsonFromUrlString(path);
		} else {
			return null;
		}
	}
	
	private static String jsonFromUrlString(String urlString) {
		try {
			URL url = new URL(urlString);
			HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
			// TODO get the bearer code from an xml file
			urlConnection.setRequestProperty("Authorization", "Bearer " + BEARER);
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
			// TODO Auto-generated catch block
			// e.printStackTrace();
			return null;
		} catch (ProtocolException e) {
			// TODO Auto-generated catch block
			// e.printStackTrace();
			return null;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			// e.printStackTrace();
			return null;
		}
	}
	
	private static String jsonUserDataFromUsername(String username, String[] userFields) {
		return jsonFromUrlString("https://api.twitter.com/2/users/by/username/" + username + userFieldsToPath(userFields, true));
		// returns null if bad request
	}
	
	private static String getIdFromUsername(String username) {
		String jsonData = jsonUserDataFromUsername(username, null);
		if (isNotNullOrEmptyString(jsonData)) {
			try {
				JsonNode jn = Caller.OBJECT_MAPPER.readTree(jsonData);
				return jn.at("/data/id").asText();
			} catch (JsonMappingException e) {
				// TODO Auto-generated catch block
				// e.printStackTrace();
				return null;
			} catch (JsonProcessingException e) {
				// TODO Auto-generated catch block
				// e.printStackTrace();
				return null;
			}
		} else {
			return null;
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
	
	/**
	 * Controlla se una stringa è vuota o null.
	 * @param string Stringa da analizzare.
	 * @return Ritorna <code>true</code> se la stringa contiene caratteri.
	 */
	public static boolean isNotNullOrEmptyString(String string) {
		if (string == null || string.isEmpty() || string.isBlank()) {
			return false;
		} else {
			return true;
		}
	}
}
