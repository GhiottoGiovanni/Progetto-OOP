package com.twitter.friends;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Controller {
	
	@Autowired
	TwitterUserService tus;
	
	@GetMapping("/stats")	
	public ResponseEntity<String> stats(@RequestParam(value = "username") String username) {
		return new ResponseEntity<String>(tus.getStatistics(username), HttpStatus.OK);
	}
	
	@GetMapping("/filter_by_word_in_description")	
	public ResponseEntity<String> filterByWordInDescription(@RequestParam(value = "username") String username, @RequestParam(value = "word") String word) {
		return new ResponseEntity<String>(tus.filterByWordInDescription(username, word), HttpStatus.OK);
	}
	
	@GetMapping("/filter_following")
	public ResponseEntity<String> filterFollowing(@RequestParam(value = "username") String username, @RequestParam(value = "friends_names") List<String> friendsNames) {
		return new ResponseEntity<String>(tus.filterIsYourFriend(username, friendsNames), HttpStatus.OK);
	}
	
	@GetMapping("/filter_followers_number")
	public ResponseEntity<String> filterFollowersNumber(@RequestParam(value = "username") String username, @RequestParam(value = "min_followers") int minFollowers) {
		return new ResponseEntity<String>(tus.filterFollowersNumber(username, minFollowers), HttpStatus.OK);
	}
	
	@GetMapping("/filter_tweets_number")
	public ResponseEntity<String> filterTweetsNumber(@RequestParam(value = "username") String username, @RequestParam(value = "min_tweets") int minTweets) {
		return new ResponseEntity<String>(tus.filterTweetsNumber(username, minTweets), HttpStatus.OK);
	}
}
