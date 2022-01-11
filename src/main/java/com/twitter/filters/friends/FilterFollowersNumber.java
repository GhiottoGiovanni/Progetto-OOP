package com.twitter.filters.friends;

import java.util.ArrayList;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.twitter.controller.Caller;
// import com.twitter.models.BasicUser;
import com.twitter.models.User;

public class FilterFollowersNumber extends FilterUsers{

	public FilterFollowersNumber(ArrayList<User> friends) {
		super(friends);
	}
	
	public String filteredData(int minFollowers) {
		ArrayNode data = Caller.OBJECT_MAPPER.createArrayNode();
		int result_count = 0;
		for (User u : getFriends()) {
			// avoids case sensitivity
			if (u.getPublic_metrics().getFollowers_count() >= minFollowers) {
				// TODO valutare se è megloio lasciare user o cambiare in basicuser
				// BasicUser bu = new BasicUser(u.getId(), u.getName(), u.getUsername());
				data.add(Caller.OBJECT_MAPPER.convertValue(u, JsonNode.class));
				result_count++;
			}
		}
		return structureData(data, result_count);
	}
}
