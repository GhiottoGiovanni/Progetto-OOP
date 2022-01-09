package com.twitter.friends;

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
	
	@GetMapping("/stats")	
	public String stats(@RequestParam(value = "username") String username) {
		String jsonTwitterUserData = Caller.jsonTwitterUserDataFromUsername(username);
		if (Caller.isNotNullOrEmptyString(jsonTwitterUserData)) {
			try {
				TwitterUser tu = Caller.OBJECT_MAPPER.readValue(jsonTwitterUserData, TwitterUser.class);
				tu.initFriends();
				
				ObjectNode root = Caller.OBJECT_MAPPER.createObjectNode();
				
				int fsan = tu.friendsFollowersAverageNumber();
				root.put("friends_followers_average_number", fsan);
				
				int fgan = tu.friendsFollowingAverageNumber();
				root.put("friends_following_average_number", fgan);
				
				int twan = tu.friendsTweetsAverageNumber();
				root.put("friends_tweets_average_number", twan);
				
				float prwd = tu.friendsPercentageWithDescription();
				root.put("friends_percentage_with_description", prwd);
				
				String jsonString = Caller.OBJECT_MAPPER.writerWithDefaultPrettyPrinter().writeValueAsString(root);
				return jsonString;
			} catch (JsonProcessingException e) {
				// TODO Auto-generated catch block
				// e.printStackTrace();
				return "Controller stats try catch 0";
			}
		} else {
			// TODO some error message
			return "Controller stats if else 0";
		}
		 
	}
	
	@GetMapping("/filter")	
	public String filter(@RequestParam(value = "username") String username, @RequestParam(value = "word") String word) {
		String jsonTwitterUserData = Caller.jsonTwitterUserDataFromUsername(username);
		if (Caller.isNotNullOrEmptyString(jsonTwitterUserData)) {
			try {
				TwitterUser tu = Caller.OBJECT_MAPPER.readValue(jsonTwitterUserData, TwitterUser.class);
				tu.initFriends();
				
				ArrayNode root = Caller.OBJECT_MAPPER.createArrayNode();
				for (BasicUser bu : tu.filterBasedOnFriendsDescription(word)) {
					root.add(Caller.OBJECT_MAPPER.convertValue(bu, JsonNode.class));
				}
				
				String jsonString = Caller.OBJECT_MAPPER.writerWithDefaultPrettyPrinter().writeValueAsString(root);
				return jsonString;
			} catch (JsonProcessingException e) {
				// TODO Auto-generated catch block
				// e.printStackTrace();
				return "Controller filter try catch 0";
			}
		} else {
			// TODO some error message
			return "Controller filters if else 0";
		}
	}
}
