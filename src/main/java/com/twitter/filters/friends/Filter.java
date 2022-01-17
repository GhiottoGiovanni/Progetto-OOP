package com.twitter.filters.friends;

import java.util.ArrayList;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.twitter.APIcaller.Caller;
import com.twitter.models.User;

/**
 * <b>Classe</b> filtro
 * @author Giovanni Ghiotto
 * @author Mihail Bobeica
 * @version 1.0
 */
abstract class Filter {
	private ArrayList<User> friends = new ArrayList<User>();
	private int friendsCount;
	
	Filter(ArrayList<User> friends) {
		setFriends(friends);
	}
	
	int getFriendsCount() {
		return friendsCount;
	}

	void setFriendsCount() {
		this.friendsCount = this.friends.size();
	}

	ArrayList<User> getFriends() {
		return friends;
	}

	void setFriends(ArrayList<User> friends) {
		this.friends = friends;
		this.setFriendsCount();
	}
	
	/**
	 * Metodo che costruisce la stringa Json contenente i dati filtrati.
	 * @param data Dati filtrati.
	 * @param result_count Lunghezza della lista.
	 * @return Stringa Json.
	 */
	public static String structureData(ArrayNode data, int result_count) {
		ObjectNode root = Caller.OBJECT_MAPPER.createObjectNode();
		
		ObjectNode meta = Caller.OBJECT_MAPPER.createObjectNode();
		meta.put("result_count", result_count);
		
		root.set("data", data);
		root.set("meta", meta);
		try {
			return Caller.OBJECT_MAPPER.writerWithDefaultPrettyPrinter().writeValueAsString(root);
		} catch (JsonProcessingException e) {
			e.toString();
			e.printStackTrace();
			return e.toString();
		}
	}
}
