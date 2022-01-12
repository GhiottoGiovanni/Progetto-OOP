package com.twitter.filters.friends;

import java.util.ArrayList;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.twitter.controller.Caller;
import com.twitter.models.User;

/**
 * Classe che eredita da {@link FilterUsers}. Filtro della lista di amici in base alla ricerca di un numero minimo di tweet.
 * @author Giovanni Ghiotto
 * @author Mihail Bobeica
 * @version 1.0
 */

public class FilterTweetsNumber extends FilterUsers{
	/**
	 * Inizializza la lista degli amici
	 * @param friends Lista di amici
	 */
	public FilterTweetsNumber(ArrayList<User> friends) {
		super(friends);
	}
	
	/**
	 * @return Ritorna la lista di amici con un determinato numero minimo di tweet.
	 */
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
