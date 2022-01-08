package com.twitter.view;

import java.util.ArrayList;

import com.twitter.models.BasicUser;
import com.twitter.models.User;

public interface Filters {
	// required Start
	ArrayList<BasicUser> filterBasedOnFriendsDescription(String word);
	// required End
	
	// optional Starts
	ArrayList<User> filterFriendsWithMostFollowers();
	
	ArrayList<User> filterFriendsWithMostTweets();
	
	boolean isThisUserMyFriend(String username);
	// optional End
}
