package com.twitter.friends;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * <b>Classe</b> controller che gestisce le chiamate  
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
	 * @param username Parola chiave per cercare le statistiche dell'utente.
	 * @return Lista delle statitische dell'utente ricercato.
	 */
	@GetMapping("/stats")	
	public ResponseEntity<String> stats(@RequestParam(value = "username") String username) {
		return new ResponseEntity<String>(tus.getStatistics(username), HttpStatus.OK);
	}
	
	/**
	 * <b>Rotta</b> per visualizzare la lisat di amici di un utente cercato con una specifica parola nella descizione del profilo.
	 * @param username Parola chiave per cercare l'utente.
	 * @param word Parola chiave specifica da cercare nella descizione degli amici.
	 * @return Lista filtrata.
	 */
	@GetMapping("/filter_by_word_in_description")	
	public ResponseEntity<String> filterByWordInDescription(@RequestParam(value = "username") String username, @RequestParam(value = "word") String word) {
		return new ResponseEntity<String>(tus.filterByWordInDescription(username, word), HttpStatus.OK);
	}
	
	/**
	 * <b>Rotta</b> per visualizzare se degli utenti sono amici del profilo selezionato.
	 * @param username Parola chiave per cercare l'utente.
	 * @param friendsNames Lista di utenti da ricercare nella lista di amici.
	 * @return Lista filtrata.
	 */
	@GetMapping("/filter_following")
	public ResponseEntity<String> filterFollowing(@RequestParam(value = "username") String username, @RequestParam(value = "friends_names") List<String> friendsNames) {
		return new ResponseEntity<String>(tus.filterIsYourFriend(username, friendsNames), HttpStatus.OK);
	}
	
	/**
	 * <b>Rotta</b> per visualizzare la lista di amici con un numero minimo di follower.
	 * @param username Parola chiave per cercare l'utente.
	 * @param minFollowers Numero minimo di follower.
	 * @return Lista filtrata.
	 */
	@GetMapping("/filter_followers_number")
	public ResponseEntity<String> filterFollowersNumber(@RequestParam(value = "username") String username, @RequestParam(value = "min_followers") int minFollowers) {
		return new ResponseEntity<String>(tus.filterFollowersNumber(username, minFollowers), HttpStatus.OK);
	}
	
	/**
	 * <b>Rotta</b> per visualizzare la lista di amici con un numero minimo di tweet.
	 * @param username Parola chiave per cercare l'utente.
	 * @param minTweets Numero minimo di tweet.
	 * @return Lista filtrata.
	 */
	@GetMapping("/filter_tweets_number")
	public ResponseEntity<String> filterTweetsNumber(@RequestParam(value = "username") String username, @RequestParam(value = "min_tweets") int minTweets) {
		return new ResponseEntity<String>(tus.filterTweetsNumber(username, minTweets), HttpStatus.OK);
	}
}
