package com.twitter.filters.friends;

import java.util.ArrayList;

import com.twitter.models.User;

/**
 * Classe filtro
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
	
	// TODO aggiungere un metodo astratto "filteredData" che restituisca una Stringa ma abbia tipo di parametri variabile
}
