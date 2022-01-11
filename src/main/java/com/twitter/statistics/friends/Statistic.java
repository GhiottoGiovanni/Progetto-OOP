package com.twitter.statistics.friends;

import java.util.ArrayList;

import com.twitter.models.User;

/**
 * Classe astratta delle statistiche
 * @author Giovanni Ghiotto
 * @author Mihail Bobeica
 * @version 1.0
 */

public abstract class Statistic {
	private ArrayList<User> friends = new ArrayList<User>();
	private int friendsCount;
	
	/**
	 * Inizializza la lista degli amici
	 * @param friends Lista di amici
	 */
	Statistic(ArrayList<User> friends) {
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
	
	int getIntValue() {
		return 0;
	}
	
	double getDoubleValue() {
		return 0d;
	}
}
