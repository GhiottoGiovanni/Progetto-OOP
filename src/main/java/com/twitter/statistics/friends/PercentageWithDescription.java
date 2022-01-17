package com.twitter.statistics.friends;

import java.util.ArrayList;

import com.twitter.models.User;
/**
 * Classe che eredita da {@link Statistic}. Calcolo della percentuale degli amici con descrizione.
 * @author Giovanni Ghiotto
 * @author Mihail Bobeica
 * @version 1.0
 */
public class PercentageWithDescription extends Statistic {
	/**
	 * Inizializza la lista degli amici
	 * @param friends Lista di amici
	 */
	public PercentageWithDescription(ArrayList<User> friends) {
		super(friends);
	}
	/**
	 * @return Ritorna la percentuale degli amici con descrizione.
	 */
	@Override
	public double getDoubleValue() {
		if (getFriendsCount() != 0) {
			int friendsWithDescription = 0;
			for (User u : getFriends()) {
				if (!u.getDescription().isBlank()) {
					friendsWithDescription++;
				}
			}
			double result = 100d * friendsWithDescription / getFriendsCount();
			// arrotondato alla seconda cifra decimale
			return Math.round(result * 100) / 100d;
		} else {
			return 0;
		}
	}
	
}
