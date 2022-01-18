package com.twitter.models;

/**
 * <b>Classe</b> contenente informazioni riguardanti l'attività di un utente.
 * @author Giovanni Ghiotto
 * @author Mihail Bobeica
 * @version 1.0
 */

public class PublicMetrics {
	/**
	 * Numero di follower.
	 */
	private int followers_count;
	/**
	 * Numero di persone seguite.
	 */
	private int following_count;
	/**
	 * Numero di tweet.
	 */
	private int tweet_count;
	/**
	 * Numero di liste.
	 */
	private int listed_count;
	
	/**
	 * Prende il numero dei follower.
	 * @return numero dei follower.
	 */
	public int getFollowers_count() {
		return followers_count;
	}
	
	/**
	 * Imposta il numero di follower.
	 * @param followers_count Numero di follower.
	 */
	public void setFollowers_count(int followers_count) {
		this.followers_count = followers_count;
	}
	
	/**
	 * Prende il numero di persone seguite.
	 * @return Numero di persone seguite.
	 */
	public int getFollowing_count() {
		return following_count;
	}
	
	/**
	 * Imposta il numero di persone seguite.
	 * @param following_count Numero di persone seguite.
	 */
	public void setFollowing_count(int following_count) {
		this.following_count = following_count;
	}
	
	/**
	 * Prende il numero di tweet.
	 * @return Numero di tweet.
	 */
	public int getTweet_count() {
		return tweet_count;
	}
	
	/**
	 * Imposta il numero di tweet.
	 * @param tweet_count Numero di tweet.
	 */
	public void setTweet_count(int tweet_count) {
		this.tweet_count = tweet_count;
	}
	
	/**
	 * Prende il numero di liste.
	 * @return numero di liste.
	 */
	public int getListed_count() {
		return listed_count;
	}
	
	/**
	 * Imposta il numero di liste. 
	 * @param listed_count Numero di liste.
	 */
	public void setListed_count(int listed_count) {
		this.listed_count = listed_count;
	}
}
