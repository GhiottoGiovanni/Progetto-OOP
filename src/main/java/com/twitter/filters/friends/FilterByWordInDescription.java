package com.twitter.filters.friends;

import java.util.ArrayList;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.twitter.controller.Caller;
import com.twitter.models.BasicUser;
import com.twitter.models.User;

public class FilterByWordInDescription extends Filter{
	
	public FilterByWordInDescription(ArrayList<User> friends) {
		super(friends);
	}
	
	public String filteredData(String word) {
		ObjectNode root = Caller.OBJECT_MAPPER.createObjectNode();
		ArrayNode data = Caller.OBJECT_MAPPER.createArrayNode();
		ObjectNode meta = Caller.OBJECT_MAPPER.createObjectNode();
		int result_count = 0;
		for (User u : getFriends()) {
			// avoids case sensitivity
			if (u.getDescription().toLowerCase().contains(word.toLowerCase())) {
				BasicUser bu = new BasicUser(u.getId(), u.getName(), u.getUsername());
				data.add(Caller.OBJECT_MAPPER.convertValue(bu, JsonNode.class));
				result_count++;
			}
		}
		meta.put("result_count", result_count);
		root.set("data", data);
		root.set("meta", meta);
		try {
			return Caller.OBJECT_MAPPER.writerWithDefaultPrettyPrinter().writeValueAsString(root);
		} catch (JsonProcessingException e) {
			// TODO some error message
			return "problema con il parsing";
		}
	}
}
