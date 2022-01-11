package com.twitter.filters.friends;

import java.util.ArrayList;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.twitter.controller.Caller;
import com.twitter.models.User;

public class FilterTweetsNumber extends FilterUsers{

	public FilterTweetsNumber(ArrayList<User> friends) {
		super(friends);
	}
	
	public String filteredData(int minTweets) {
		ArrayNode data = Caller.OBJECT_MAPPER.createArrayNode();
		int result_count = 0;
		for (User u : getFriends()) {
			if (u.getPublic_metrics().getTweet_count() >= minTweets) {
				data.add(Caller.OBJECT_MAPPER.convertValue(u, JsonNode.class));
				result_count++;
			}
		}
		return structureData(data, result_count);
	}
}
