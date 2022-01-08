package com.twitter.friends;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.twitter.controller.Caller;
import com.twitter.models.TwitterUser;

@RestController
public class Controller {
	@GetMapping("/stats")	
	public String stats(@RequestParam(value = "username") String username) {
		try {
			TwitterUser tu = Caller.OBJECT_MAPPER.readValue(Caller.jsonTwitterUserDataFromUsername(username), TwitterUser.class);
			tu.initFriends();
			int fsan = tu.friendsFollowersAverageNumber();
			ObjectNode root = Caller.OBJECT_MAPPER.createObjectNode();
			root.put("fsan", fsan);
			String jsonString = Caller.OBJECT_MAPPER.writerWithDefaultPrettyPrinter().writeValueAsString(root);
			return jsonString;
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "error";
		}
	}
}
