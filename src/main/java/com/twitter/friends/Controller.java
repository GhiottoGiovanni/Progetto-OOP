package com.twitter.friends;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.twitter.exceptions.NegativeNumberException;

/**
 * <b>Classe</b> controller che gestisce le chiamate.
 * @author Giovanni Ghiotto
 * @author Mihail Bobeica
 * @version 1.0
 */

@RestController
public class Controller {
	
	@Autowired
	TwitterUserService tus;
	
	/**
	 * <b>Rotta</b> per visualizzare le statistiche di un utente ricercato.
	 * @param username Nome identificativo dell'account Twitter.
	 * @return Risultato della chiamata.
	 */
	@GetMapping("/stats")	
	public ResponseEntity<String> stats(@RequestParam(value = "username") String username) {
		return new ResponseEntity<String>(tus.getStatistics(username), HttpStatus.OK);
	}
	
	/**
	 * <b>Rotta</b> per visualizzare se degli utenti sono amici del profilo selezionato.
	 * @param username Nome identificativo dell'account Twitter.
	 * @param friendsNames Lista di utenti da ricercare nella lista di amici.
	 * @return Risultato della chiamata.
	 */
	@GetMapping("/filter_following")
	public ResponseEntity<String> filterFollowing(@RequestParam(value = "username") String username, @RequestParam(value = "friends_names") List<String> friendsNames) {
		return new ResponseEntity<String>(tus.filterIsYourFriend(username, friendsNames), HttpStatus.OK);
	}
	
	/**
	 * <b>Rotta</b> per visualizzare la lista completa di amici.
	 * @param username Nome identificativo dell'account Twitter.
	 * @return Risultato della chiamata.
	 */
	@GetMapping("/get_all_friends")
	public ResponseEntity<String> getAllFriends(@RequestParam(value = "username") String username){
		return new ResponseEntity<String>(tus.getAllFriends(username), HttpStatus.OK);
	}
	
	/**
	 * <b>Rotta</b> per filtrare la lista degli amici in base ad una serie di parametri.
	 * @param username Nome identificativo dell'account Twitter.
	 * @param word Parola cercata nella descrizione.
	 * @param minTweets Numero minimo di tweet.
	 * @param minFollowers Numero minimo di follower.
	 * @return Risultato della chiamata.
	 */
	@GetMapping("/filter")
	public ResponseEntity<String> filter(@RequestParam(value = "username") String username,
			@RequestParam(value = "word", required = false) String word,
			@RequestParam(value = "min_tweets", required = false) String minTweets,
			@RequestParam(value = "min_followers", required = false) String minFollowers){
		Integer min_tweets = null;
		if (minTweets != null) {
			try {
				min_tweets = Integer.parseInt(minTweets);
			} catch (NumberFormatException e) {
				return new ResponseEntity<String>("min_tweets DEVE ESSERE UN NUMERO! (valore inserito: " + minTweets + ")", HttpStatus.BAD_REQUEST);
			}
		}

		Integer min_followers = null;
		if (minFollowers != null) {
			try {
				min_followers = Integer.parseInt(minFollowers);
			} catch (NumberFormatException e) {
				return new ResponseEntity<String>("min_followers DEVE ESSERE UN NUMERO! (valore inserito: " + minFollowers + ")", HttpStatus.BAD_REQUEST);
			}
		}
		
		String result;
		try {
			result = tus.filter(username, word, min_tweets, min_followers);
			if (result != null) {
				return new ResponseEntity<String>(result, HttpStatus.OK);
			} else {
				return new ResponseEntity<String>("Devi filtrare rispetto ad almeno uno dei seguenti parametri:\nword = parola nella descrizione;\nmin_tweets = numero minimo di tweet;\nmin_followers = numero minimo di follower.", HttpStatus.BAD_REQUEST);
			}
		} catch (NegativeNumberException e) {
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}
}
