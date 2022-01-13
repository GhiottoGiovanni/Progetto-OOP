package com.twitter.friends;

import java.util.List;

/**
 * <b>Interfaccia</b> di servizio per gestire le operazioni sugli amici di un utente.
 * @author Giovanni Ghiotto
 * @author Mihail Bobeica
 * @version 1.0
 */

public interface TwitterUserService {
	
	/**
	 * <b>Intestazione</b> del metodo che fornisce le statistiche di un utente.
	 * @param username Nome identificativo dell'account Twitter.
	 * @return Stringa Json delle statistiche di un utente.
	 */
	String getStatistics(String username);
	
	/**
	 * <b>Intestazione</b> del metodo che filtra in base a una parola nella descrizione.
	 * @param username Nome identificativo dell'account Twitter.
	 * @param word Parola chiave cercata nella descrizione.
	 * @return Lista filtrata in formato Json. 
	 */
	String filterByWordInDescription(String username, String word);
	
	/**
	 * <b>Intestazione</b> del metodo che indica se un determinato nome è presente o meno nella lista amici.
	 * @param username Nome identificativo dell'account Twitter.
	 * @param friendsNames Parola chiave di ricerca amico.
	 * @return Lista dei risultati in formato Json.
	 */
	String filterIsYourFriend(String username, List<String> friendsNames);
	
	/**
	 * <b>Intestazione</b> del metodo che filtra in base al numero minimo di follower.
	 * @param username Nome identificativo dell'account Twitter.
	 * @param minFollowers Numero minimo di follower.
	 * @return Lista filtarta in formato Json.
	 */
	String filterFollowersNumber(String username, int minFollowers);
	
	/**
	 * <b>Intestazione</b> del metodo che filtra in base al numero minimo di tweet.
	 * @param username Nome identificativo dell'account Twitter.
	 * @param minTweets Numero minimo di tweet.
	 * @return Lista filtarta in formato Json.
	 */
	String filterTweetsNumber(String username, int minTweets);
	
}
