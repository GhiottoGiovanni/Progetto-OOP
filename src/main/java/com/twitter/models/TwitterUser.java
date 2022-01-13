package com.twitter.models;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.exc.StreamWriteException;
import com.fasterxml.jackson.databind.DatabindException;
import com.fasterxml.jackson.databind.JsonNode;
import com.twitter.APIcaller.Caller;

/**
 * <b>Classe</b> utente che eredita da {@link User}.
 * Rispetto alla classe {@link User} contiene una lista di <i>friends</i> (utenti che l'utente segue).
 * @author Giovanni Ghiotto
 * @author Mihail Bobeica
 * @version 1.0
 */

public class TwitterUser extends User{
	
	/**
	 * Nome completo della cartella dove vengono temporaneamente salvati i dati dei <i>friends</i> di un utente.
	 */
	public final static String FRIENDS_STORAGE_DIR = "./src/main/resources/data/friends";
	/**
	 * Percorso completo del file dove vengono temporaneamente salvati i dati dei <i>friends</i> di un utente.
	 */
	private final static String FRIENDS_STORAGE_PATH = FRIENDS_STORAGE_DIR + "/";
	/**
	 * <p>Indica se i dati dei <i>friends</i> di un utente vengono o meno salvati in locale.</p>
	 * 	<ul>
	 * 		<li>Impostare <code>false</code> se si desidera caricare i dati sempre dalla Twitter API.</li>
	 * 		<li>Impostare <code>true</code> se si desidera che i dati dei <i>friends</i> di un utente vengano temporaneamente salvati in locale.<br>
	 * Ciò <b>riduce</b> i tempi di chiamata e permette di fare un numero <b>non</b> limitato di chiamate (in riferimento all'utente i cui dati vengono salvati in locale).</li>
	 * </ul>
	 */
	public final static boolean STORE_LOCALY = true;
	
	/**
	 * Lista dei <i>friends</i> (di tipo {@link User} di un utente
	 */
	private ArrayList<User>	friends = new ArrayList<User>();
	/**
	 * Dimensione della lista degli utenti
	 */
	private int friends_count;
	
	// TODO add constructors if needed
	
	public ArrayList<User> getFriends() {
		return friends;
	}
	
	public int getFriends_count() {
		return friends_count;
	}

	private void setFriends_count(int friends_count) {
		this.friends_count = friends_count;
	}
	
	/**
	 * 
	 * @return Ritorna il nome completo del file json dove vengono temporaneamente salvati i dati dei <i>friends</i>. 
	 */
	private String getFriendsFileName() {
		return FRIENDS_STORAGE_PATH + this.getUsername() + ".json";
	}
	
	/**
	 * <p>Inizializza la lista di <i>friends</i>.</p>
	 * <p>Se il file json esiste e se {@link STORE_LOCALY} è <code>true</code> carica i dati dei <i>friends</i> da locale, altrimenti li carica tramite una chiamata alla Twitter API.
	 */
	public void initFriends() {
		if (new File(getFriendsFileName()).exists() && STORE_LOCALY) {
			initFriendsFromLocal();
		} else {
			initFriendsFromAPIrequest();
		}
	}

	private void initFriendsFromLocal() {
		try {
			User[] friends = Caller.OBJECT_MAPPER.readValue(Paths.get(getFriendsFileName()).toFile(), User[].class);
			Collections.addAll(this.friends, friends);
			this.setFriends_count(this.friends.size());
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			// e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			// e.printStackTrace();
		}
	}
	
	private void initFriendsFromAPIrequest() {
		String nextToken = null;
		do {
			String friendsData = Caller.followingDataFromUsername(this.getUsername(), nextToken);
			try {
				JsonNode jsonNode = Caller.OBJECT_MAPPER.readTree(friendsData);
				
				// get next token
				nextToken = jsonNode.at("/meta/next_token").asText();
				
				// get a friends segment and add it to the friends array list
				User[] friendsSegment =  Caller.OBJECT_MAPPER.readValue(jsonNode.at("/data").toString(), User[].class);
				for (User u : friendsSegment) {
					this.friends.add(u);
				}
			} catch (JsonProcessingException e) {
				e.printStackTrace();
			}
		} while (nextToken != null && !nextToken.isBlank());
		this.setFriends_count(this.friends.size());
		
		// store friends data
		if (STORE_LOCALY) {
			try {
				Caller.OBJECT_MAPPER.writeValue(Paths.get(getFriendsFileName()).toFile(), this.friends);
				System.out.println("App: file temporaneo " + this.getUsername() + ".json salvato!");
			} catch (StreamWriteException e) {
				// TODO Auto-generated catch block
				// e.printStackTrace();
			} catch (DatabindException e) {
				// TODO Auto-generated catch block
				// e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				// e.printStackTrace();
			}
		}
	}
}
