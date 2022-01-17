package com.twitter.filters.friends;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.twitter.APIcaller.Caller;
import com.twitter.models.User;

/**
 * <b>Classe</b> che eredita da {@link Filter}. Controlla se un determinato utente è tuo amico.
 * @author Giovanni Ghiotto
 * @author Mihail Bobeica
 * @version 1.0
 */

public class FilterIsYourFriend extends Filter{
	/**
	 * Inizializza la lista degli amici
	 * @param friends Lista di amici
	 */
	public FilterIsYourFriend(ArrayList<User> friends) {
		super(friends);
	}
	
	/**
	 * @return Ritorna se un utente selezionato è tuo amico.
	 */
	private boolean isFollowing(String friendName) {
		for (User u : getFriends()) {
			// avoids case sensitivity
			if (u.getName().toLowerCase().equals(friendName.toLowerCase())) {
				return true;
			}
		}
		return false;
	}

	/**
	 * @param friendsNames Nomi dei probabili amici.
	 * @return Stringa Json contenente i risultati.
	 */
	public String filteredData(List<String> friendsNames) {
		ArrayNode data = Caller.OBJECT_MAPPER.createArrayNode();
		int result_count = 0;
		for (String friendName : friendsNames) {
			ObjectNode node = Caller.OBJECT_MAPPER.createObjectNode();
			node.put("name", friendName);
			node.put("following", isFollowing(friendName));
			data.add(node);
			result_count++;
		}
		return structureData(data, result_count);
	}
}
