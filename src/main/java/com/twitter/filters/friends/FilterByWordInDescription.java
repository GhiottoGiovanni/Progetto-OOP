package com.twitter.filters.friends;

import java.util.ArrayList;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.twitter.controller.Caller;
import com.twitter.models.BasicUser;
import com.twitter.models.User;

public class FilterByWordInDescription extends FilterUsers{
	
	public FilterByWordInDescription(ArrayList<User> friends) {
		super(friends);
	}
	
	public String filteredData(String word) {
		ArrayNode data = Caller.OBJECT_MAPPER.createArrayNode();
		int result_count = 0;
		for (User u : getFriends()) {
			// avoids case sensitivity
			if (u.getDescription().toLowerCase().contains(word.toLowerCase())) {
				BasicUser bu = new BasicUser(u.getId(), u.getName(), u.getUsername());
				data.add(Caller.OBJECT_MAPPER.convertValue(bu, JsonNode.class));
				result_count++;
			}
		}
		return structureData(data, result_count);
	}
}
