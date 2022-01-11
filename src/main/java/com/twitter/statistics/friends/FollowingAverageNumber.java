package com.twitter.statistics.friends;

import java.util.ArrayList;

import com.twitter.models.User;

public class FollowingAverageNumber extends Statistic{
	
	public FollowingAverageNumber(ArrayList<User> friends) {
		super(friends);
	}
	
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
