package com.twitter.statistics.friends;

import java.util.ArrayList;

import com.twitter.models.User;
/**
 * <b>Classe</b> che eredita da {@link Statistic}. Calcolo del numero medio dei follower degli amici.
 * @author Giovanni Ghiotto
 * @author Mihail Bobeica
 * @version 1.0
 */
public class FollowersAverageNumber extends Statistic{
	/**
	 * Inizializza la lista degli amici
	 * @param friends Lista di amici
	 */
	public FollowersAverageNumber(ArrayList<User> friends) {
		super(friends);
	}
	/**
	 * @return Ritorna il numero medio dei follower degli amici.
	 */
	@Override
	public int getIntValue() {
		if (getFriendsCount() != 0) {
			int sum = 0;
			for (User u : getFriends()) {
				sum += u.getPublic_metrics().getFollowers_count();
			}
			return sum / getFriendsCount();
		} else {
			return 0;
		}
	}
	
}
