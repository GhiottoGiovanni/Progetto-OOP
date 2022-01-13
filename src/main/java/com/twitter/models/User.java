package com.twitter.models;

/**
 * <b>Classe</b> utente che eredita da {@link BasicUser}.
 * Rispetto alla classe {@link BasicUser} contiene una serie di informazioni riguardanti il profilo dell'utente.
 * @author Mihail Bobeica
 * @version 1.0
 */

public class User extends BasicUser{
	/**
	 * Descrizione del profilo dell'utente
	 */
	private String description;
	/**
	 * Posizione indicata nel profilo dell'utente
	 */
	private String location;
	/**
	 * Indica se l'utente è un utente Twitter verificato
	 */
	private boolean verified;
	/**
	 * Contiene dati riguardanti l'attività dell'utente
	 */
	private PublicMetrics public_metrics;
	
	// TODO add constructors if needed
	
	public String getDescription() {
		return description;
	}
	
	public void setDescription(String description) {
		this.description = description;
	}
	
	public String getLocation() {
		return location;
	}
	
	public void setLocation(String location) {
		this.location = location;
	}
	
	public boolean isVerified() {
		return verified;
	}
	
	public void setVerified(boolean verified) {
		this.verified = verified;
	}
	
	public PublicMetrics getPublic_metrics() {
		return public_metrics;
	}
	
	public void setPublic_metrics(PublicMetrics public_metrics) {
		this.public_metrics = public_metrics;
	}
}
