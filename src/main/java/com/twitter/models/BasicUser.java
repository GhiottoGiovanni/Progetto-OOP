package com.twitter.models;

/**
 * <b>Classe</b> utente di base.
 * @author Giovanni Ghiotto
 * @author Mihail Bobeica
 * @version 1.0
 */

public class BasicUser {
	/**
	 * ID dell'utente.
	 */
	private String id;
	/**
	 * Nome dell'utente.
	 */
	private String name;
	/**
	 * Nome identificativo dell'utente.
	 */
	private String username;
	
	/**
	 * Prende l'ID dell'utente.
	 * @return ID dell'utente.
	 */
	public String getId() {
		return id;
	}
	
	/**
	 * Imposta l'ID dell'utente.
	 * @param id ID dell'utente.
	 */
	public void setId(String id) {
		this.id = id;
	}
	
	/**
	 * Prende il nome dell'utente.
	 * @return Nome dell'utente.
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * Imposta il nome dell'utente.
	 * @param name Nome dell'utente.
	 */
	public void setName(String name) {
		this.name = name;
	}
	
	/**
	 * Prende l'username dell'utente.
	 * @return username dell'utente.
	 */
	public String getUsername() {
		return username;
	}
	
	/**
	 * Imposta l'username dell'utente.
	 * @param username Username dell'utente.
	 */
	public void setUsername(String username) {
		this.username = username;
	}	
}
