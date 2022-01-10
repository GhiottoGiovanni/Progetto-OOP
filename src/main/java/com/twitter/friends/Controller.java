package com.twitter.friends;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.twitter.controller.Caller;
import com.twitter.models.BasicUser;
import com.twitter.models.TwitterUser;

@RestController
public class Controller {
	
	private TwitterUser initTwitterUser(String username) {
		String jsonTwitterUserData = Caller.jsonTwitterUserDataFromUsername(username);
		if (Caller.isNotNullOrEmptyString(jsonTwitterUserData)) {
			try {
				TwitterUser tu = Caller.OBJECT_MAPPER.readValue(jsonTwitterUserData, TwitterUser.class);
				tu.initFriends();
				return tu;
			} catch (JsonProcessingException e) {
				return null;
			}
		} else {
			return null;
		}
	}
	
	@GetMapping("/stats")	
	public String stats(@RequestParam(value = "username") String username) {
		TwitterUser tu = initTwitterUser(username);
		if (tu != null) {
			ObjectNode root = Caller.OBJECT_MAPPER.createObjectNode();
			
			int fsan = tu.friendsFollowersAverageNumber();
			root.put("friends_followers_average_number", fsan);
			
			int fgan = tu.friendsFollowingAverageNumber();
			root.put("friends_following_average_number", fgan);
			
			int twan = tu.friendsTweetsAverageNumber();
			root.put("friends_tweets_average_number", twan);
			
			float prwd = tu.friendsPercentageWithDescription();
			root.put("friends_percentage_with_description", prwd);
			
			try {
				return Caller.OBJECT_MAPPER.writerWithDefaultPrettyPrinter().writeValueAsString(root);
			} catch (JsonProcessingException e) {
				// TODO some error message
				return "problema con il parsing";
			}
		} else {
			// TODO some error message
			return "utente twitter non definito";
		}
	}
	
	@GetMapping("/filter_by_description")	
	public String filterByDescription(@RequestParam(value = "username") String username, @RequestParam(value = "word") String word) {
		TwitterUser tu = initTwitterUser(username);
		if (tu != null) {
			ArrayNode root = Caller.OBJECT_MAPPER.createArrayNode();
			
			for (BasicUser bu : tu.filterBasedOnFriendsDescription(word)) {
				root.add(Caller.OBJECT_MAPPER.convertValue(bu, JsonNode.class));
			}
			
			try {
				return Caller.OBJECT_MAPPER.writerWithDefaultPrettyPrinter().writeValueAsString(root);
			} catch (JsonProcessingException e) {
				// TODO some error message
				return "problema con il parsing";
			}
		} else {
			// TODO some error message
			return "utente twitter non definito";
		}
	}
	
	@GetMapping("/filter_following")
	public String filterFollowing(@RequestParam(value = "username") String username, @RequestParam(value = "friends_usernames") List<String> friendsNames) {
		TwitterUser tu = initTwitterUser(username);
		if (tu != null) {
			ObjectNode root = Caller.OBJECT_MAPPER.createObjectNode();
			
			for (String friendName : friendsNames) {
				boolean following = tu.isThisUserMyFriend(friendName);
				root.put("following_%s".formatted(friendName), following);
			}
			
			try {
				return Caller.OBJECT_MAPPER.writerWithDefaultPrettyPrinter().writeValueAsString(root);
			} catch (JsonProcessingException e) {
				// TODO some error message
				return "problema con il parsing";
			}
		} else {
			// TODO some error message
			return "utente twitter non definito";
		}
	}
}
