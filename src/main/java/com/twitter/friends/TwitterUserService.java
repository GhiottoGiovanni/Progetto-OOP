package com.twitter.friends;

import java.util.List;

import com.twitter.exceptions.NegativeNumberException;

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
	 * <b>Intestazione</b> del metodo che indica se un determinato nome è presente o meno nella lista amici.
	 * @param username Nome identificativo dell'account Twitter.
	 * @param friendsNames Parola chiave di ricerca amico.
	 * @return Lista dei risultati in formato Json.
	 */
	String filterIsYourFriend(String username, List<String> friendsNames);
	
	/**
	 * <b>Intestazione</b> del metodo che mostra la lista di tutti gli amici.
	 * @param username Nome identificativo dell'account Twitter.
	 * @return Lista completa degli amici.
	 */
	String getAllFriends(String username);
	
	
	/**
	 * <b>Intestazione</b> del metodo che filtra la lista degli amici in base ai parametri dati.
	 * @param username Nome identificativo dell'account Twitter.
	 * @param word Parola cercata nella descrizione.
	 * @param minTweets Numero minimo di tweet.
	 * @param minFollowers Numero minimo di follower.
	 * @return Lista filtrata.
	 * @throws NegativeNumberException Avvisa in caso di inserimento di valori negativi.
	 */
	String filter(String username, String word, Integer minTweets, Integer minFollowers) throws NegativeNumberException;
}
