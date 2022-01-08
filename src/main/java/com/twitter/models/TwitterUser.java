package com.twitter.models;

import java.util.ArrayList;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.twitter.view.Filters;
import com.twitter.view.Statistics;

import com.twitter.controller.Caller;

public class TwitterUser extends User implements Filters, Statistics{
	private ArrayList<User>	friends = new ArrayList<User>();
	private int friends_count;
	
	// TODO add constructors if needed

	public ArrayList<User> getFriends() {
		return friends;
	}
	
	public int getFriends_count() {
		return friends_count;
	}

	private void setFriends_count(int friends_count) {
		this.friends_count = friends_count;
	}

	public void initFriends() {
		String nextToken = null;
		do {
			String friendsData = Caller.followingDataFromUsername(this.getUsername(), nextToken);
			try {
				JsonNode jsonNode = Caller.OBJECT_MAPPER.readTree(friendsData);
				
				// get next token
				nextToken = jsonNode.at("/meta/next_token").asText();
				
				// get a friends segment and add it to the friends array list
				User[] friendsSegment =  Caller.OBJECT_MAPPER.readValue(jsonNode.at("/data").toString(), User[].class);
				for (User u : friendsSegment) {
					this.friends.add(u);
				}
			} catch (JsonProcessingException e) {
				e.printStackTrace();
			}
		} while (nextToken != null && !nextToken.isBlank());
		this.setFriends_count(friends.size());
	}
	
	// statistics START
	@Override
	public int friendsFollowersAverageNumber() {
		int sum = 0;
		for (User u : this.friends) {
			sum += u.getPublic_metrics().getFollowers_count();
		}
		return sum / this.friends_count;
	}
	
	@Override
	public int friendsFollowingAverageNumber() {
		int sum = 0;
		for (User u : this.friends) {
			sum += u.getPublic_metrics().getFollowing_count();
		}
		return sum / this.friends_count;
	}

	@Override
	public float friendsPercentageWithDescription() {
		int friendsWithDescription = 0;
		for (User u : this.friends) {
			if (!u.getDescription().isBlank()) {
				friendsWithDescription++;
			}
		}
		return (float)friendsWithDescription / this.friends_count * 100;
	}

	@Override
	public int friendsTweetsAverageNumber() {
		int sum = 0;
		for (User u : this.friends) {
			sum += u.getPublic_metrics().getTweet_count();
		}
		return sum / this.friends_count;
	}
	// statistics END
	
	// filters START
	@Override
	public ArrayList<BasicUser> filterBasedOnFriendsDescription(String word) {
		ArrayList<BasicUser> fs = new ArrayList<BasicUser>();
		for (User u : this.friends) {
			// avoids case sensitivity
			if (u.getDescription().toLowerCase().contains(word.toLowerCase())) {
				BasicUser bu = new BasicUser(u.getId(), u.getName(), u.getUsername());
				fs.add(bu);
			}
		}
		return fs;
	}

	@Override
	public ArrayList<User> filterFriendsWithMostFollowers() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ArrayList<User> filterFriendsWithMostTweets() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isThisUserMyFriend(String username) {
		for (User u : this.friends) {
			if (u.getName().equals(username)) {
				return true;
			}
		}
		return false;
	}
	// filters END
}
