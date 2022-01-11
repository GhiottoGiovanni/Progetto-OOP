package com.twitter.statistics.friends;

import java.util.ArrayList;

import com.twitter.models.User;

public class Statistic {
	private ArrayList<User> friends = new ArrayList<User>();
	private int friendsCount;
	
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
		return 0f;
	}
}
