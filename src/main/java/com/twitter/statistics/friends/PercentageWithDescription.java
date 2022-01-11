package com.twitter.statistics.friends;

import java.util.ArrayList;

import com.twitter.models.User;

public class PercentageWithDescription extends Statistic {
	
	public PercentageWithDescription(ArrayList<User> friends) {
		super(friends);
	}
	
	@Override
	public double getDoubleValue() {
		if (getFriendsCount() != 0) {
			int friendsWithDescription = 0;
			for (User u : getFriends()) {
				if (!u.getDescription().isBlank()) {
					friendsWithDescription++;
				}
			}
			return 100d * friendsWithDescription / getFriendsCount();
		} else {
			return 0;
		}
	}
	
}
