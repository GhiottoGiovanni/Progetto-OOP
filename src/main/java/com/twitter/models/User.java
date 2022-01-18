package com.twitter.models;

/**
 * <b>Classe</b> utente che eredita da {@link BasicUser}.
 * Rispetto alla classe {@link BasicUser} contiene una serie di informazioni riguardanti il profilo dell'utente.
 * @author Giovanni Ghiotto
 * @author Mihail Bobeica
 * @version 1.0
 */

public class User extends BasicUser{
	/**
	 * Descrizione del profilo dell'utente.
	 */
	private String description;
	/**
	 * Posizione indicata nel profilo dell'utente.
	 */
	private String location;
	/**
	 * Indica se l'utente è un utente Twitter verificato.
	 */
	private boolean verified;
	/**
	 * Contiene dati riguardanti l'attività dell'utente.
	 */
	private PublicMetrics public_metrics;
	
	/**
	 * Prende la descrizione del profilo.
	 * @return Descrizione del profilo.
	 */
	public String getDescription() {
		return description;
	}
	
	/**
	 * Imposta la descrizione del profilo.
	 * @param description Descrizione del profilo.
	 */
	public void setDescription(String description) {
		this.description = description;
	}
	
	/**
	 * Prende la posizione indicata nel profilo.
	 * @return Posizione indicata nel profilo.
	 */
	public String getLocation() {
		return location;
	}
	
	/**
	 * Imposta posizione indicata nel profilo.
	 * @param location posizione indicata nel profilo.
	 */
	public void setLocation(String location) {
		this.location = location;
	}
	
	/**
	 * Controlla se l'utente è un utente Twitter verificato.
	 * @return Indica se l'utente è un utente Twitter verificato.
	 */
	public boolean isVerified() {
		return verified;
	}
	
	/**
	 * Imposta che l'utente sia o meno un utente Twitter verificato.
	 * @param verified
	 */
	public void setVerified(boolean verified) {
		this.verified = verified;
	}
	
	/**
	 * Prende i dati riguardanti l'attività dell'utente.
	 * @return
	 * @see PublicMetrics
	 */
	public PublicMetrics getPublic_metrics() {
		return public_metrics;
	}
	
	/**
	 * Imposta i dati riguardanti l'attività dell'utente.
	 * @param public_metrics 
	 * @see PublicMetrics
	 */
	public void setPublic_metrics(PublicMetrics public_metrics) {
		this.public_metrics = public_metrics;
	}
}
