package com.twitter.filters.friends;

import java.util.ArrayList;

import com.twitter.models.User;

/**
 * <b>Classe</b> che eredita da {@link FilterUsers}. Filtro della lista di amici in base ad una parola contenuta nella descrizione.
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

	@Override
	public <T> ArrayList<User> filter(T x) {
		ArrayList<User> filteredUsers = new ArrayList<User>();
		for (User u : getFriends()) {
			// avoids case sensitivity
			if (u.getDescription().toLowerCase().contains(((String) x).toLowerCase())) {
				filteredUsers.add(u);
			}
		}
		return filteredUsers;
	}
}
