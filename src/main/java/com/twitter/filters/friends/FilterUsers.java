package com.twitter.filters.friends;

import java.util.ArrayList;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.twitter.controller.Caller;
import com.twitter.models.User;

abstract class FilterUsers extends Filter{
	
	public FilterUsers(ArrayList<User> friends) {
		super(friends);
	}
	
	String structureData(ArrayNode data, int result_count) {
		ObjectNode root = Caller.OBJECT_MAPPER.createObjectNode();
		
		ObjectNode meta = Caller.OBJECT_MAPPER.createObjectNode();
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
