package com.twitter.filters.friends;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.twitter.controller.Caller;
import com.twitter.models.User;

public class FilterIsYourFriend extends Filter{

	public FilterIsYourFriend(ArrayList<User> friends) {
		super(friends);
	}
	
	private boolean isFollowing(String friendName) {
		for (User u : getFriends()) {
			// avoids case sensitivity
			if (u.getName().toLowerCase().equals(friendName.toLowerCase())) {
				return true;
			}
		}
		return false;
	}
	
	public String filteredData(List<String> friendsNames) {
		ObjectNode root = Caller.OBJECT_MAPPER.createObjectNode();
		for (String friendName : friendsNames) {
			root.put("following_%s".formatted(friendName), isFollowing(friendName));
		}
		try {
			return Caller.OBJECT_MAPPER.writerWithDefaultPrettyPrinter().writeValueAsString(root);
		} catch (JsonProcessingException e) {
			// TODO some error message
			return "problema con il parsing";
		}
	}
}