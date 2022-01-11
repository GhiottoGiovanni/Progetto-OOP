package com.twitter.filters.friends;

import java.util.ArrayList;

import com.twitter.models.User;

public abstract class Filter {
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
}
