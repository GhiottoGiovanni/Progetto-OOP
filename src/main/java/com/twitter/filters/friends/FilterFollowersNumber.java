package com.twitter.filters.friends;

import java.util.ArrayList;

import com.twitter.models.User;

/**
 * <b>Classe</b> che eredita da {@link FilterUsers}. Filtro della lista di amici in base alla ricerca di un numero minimo di follower.
 * @author Giovanni Ghiotto
 * @author Mihail Bobeica
 * @version 1.0
 */

public class FilterFollowersNumber extends FilterUsers{
	/**
	 * Inizializza la lista degli amici
	 * @param friends Lista di amici
	 */
	public FilterFollowersNumber(ArrayList<User> friends) {
		super(friends);
	}

	@Override
	public <T> ArrayList<User> filter(T x) {
		ArrayList<User> filteredUsers = new ArrayList<User>();
		for (User u : getFriends()) {
			if (u.getPublic_metrics().getFollowers_count() >= (int) x) {
				filteredUsers.add(u);
			}
		}
		return filteredUsers;
	}
}
