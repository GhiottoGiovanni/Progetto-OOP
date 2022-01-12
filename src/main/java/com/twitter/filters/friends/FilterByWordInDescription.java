package com.twitter.filters.friends;

import java.util.ArrayList;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.twitter.controller.Caller;
import com.twitter.models.User;

/**
 * Classe che eredita da {@link FilterUsers}. Filtro della lista di amici in base ad una parola contenuta nella descrizione.
 * @author Giovanni Ghiotto
 * @author Mihail Bobeica
 * @version 1.0
 */

public class FilterByWordInDescription extends FilterUsers{
	/**
	 * Inizializza la lista degli amici
	 * @param friends Lista di amici
	 */
	public FilterByWordInDescription(ArrayList<User> friends) {
		super(friends);
	}
	
	/**
	 * @return Ritorna la lista di amici la descizione dei quali contiene una parola data.
	 */
	public String filteredData(String word) {
		ArrayNode data = Caller.OBJECT_MAPPER.createArrayNode();
		int result_count = 0;
		for (User u : getFriends()) {
			// avoids case sensitivity
			if (u.getDescription().toLowerCase().contains(word.toLowerCase())) {
				data.add(Caller.OBJECT_MAPPER.convertValue(u, JsonNode.class));
				result_count++;
			}
		}
		return structureData(data, result_count);
	}
}
