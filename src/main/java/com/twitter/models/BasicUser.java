package com.twitter.models;

/**
 * <b>Classe</b> utente di base
 * @author Mihail Bobeica
 * @version 1.0
 */

public class BasicUser {
	/**
	 * ID dell'utente
	 */
	private String id;
	/**
	 * Nome dell'utente
	 */
	private String name;
	/**
	 * Nome identificativo dell'utente
	 */
	private String username;
	
	// TODO add constructors if needed
	public BasicUser() {
		
	}
	
	public BasicUser(String id, String name, String username) {
		this.id = id;
		this.name = name;
		this.username = username;
	}
	
	public String getId() {
		return id;
	}
	
	public void setId(String id) {
		this.id = id;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getUsername() {
		return username;
	}
	
	public void setUsername(String username) {
		this.username = username;
	}	
}
