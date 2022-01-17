package com.twitter.statistics.friends;

import java.util.ArrayList;

import com.twitter.models.User;
/**
 * <b>Classe</b> che eredita da {@link Statistic}. Calcolo del numero medio degli amici degli amici.
 * @author Giovanni Ghiotto
 * @author Mihail Bobeica
 * @version 1.0
 */
public class FollowingAverageNumber extends Statistic{
	/**
	 * Inizializza la lista degli amici.
	 * @param friends Lista di amici.
	 */
	public FollowingAverageNumber(ArrayList<User> friends) {
		super(friends);
	}
	/**
	 * @return Ritorna il numero medio degli amici degli amici.
	 */
	@Override
	public int getIntValue() {
		if (getFriendsCount() != 0) {
			int sum = 0;
			for (User u : getFriends()) {
				sum += u.getPublic_metrics().getFollowing_count();
			}
			return sum / getFriendsCount();
		} else {
			return 0;
		}
	}
}
