package com.twitter.models;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.exc.StreamWriteException;
import com.fasterxml.jackson.databind.DatabindException;
import com.fasterxml.jackson.databind.JsonNode;
import com.twitter.view.Filters;
import com.twitter.view.Statistics;

import com.twitter.controller.Caller;

public class TwitterUser extends User implements Filters, Statistics{
	
	// private static String USERS_STORAGE_PATH = "./src/main/resources/data/usernames/";
	private static String FRIENDS_STORAGE_PATH = "./src/main/resources/data/friends/";
	
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
	
	private String getFriendsFileName() {
		return FRIENDS_STORAGE_PATH + this.getUsername() + ".json";
	}
	
	public void initFriends() {
		// TODO add an auto delete data when closed option
		if (new File(getFriendsFileName()).exists()) {
			initFriendsFromLocal();
		} else {
			initFriendsFromRequest();
		}
	}
	
	private void initFriendsFromLocal() {
		try {
			User[] friends = Caller.OBJECT_MAPPER.readValue(Paths.get(getFriendsFileName()).toFile(), User[].class);
			Collections.addAll(this.friends, friends);
			this.setFriends_count(this.friends.size());
			System.out.println("dati presi e caricati correttamente");
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			// e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			// e.printStackTrace();
		}
	}

	private void initFriendsFromRequest() {
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
		this.setFriends_count(this.friends.size());
		
		// store friends data
		try {
			Caller.OBJECT_MAPPER.writeValue(Paths.get(getFriendsFileName()).toFile(), this.friends);
		} catch (StreamWriteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (DatabindException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	// statistics START
	@Override
	public int friendsFollowersAverageNumber() {
		if (this.friends_count != 0) {
			int sum = 0;
			for (User u : this.friends) {
				sum += u.getPublic_metrics().getFollowers_count();
			}
			return sum / this.friends_count;
		} else {
			return 0;
		}
	}
	
	@Override
	public int friendsFollowingAverageNumber() {
		if (this.friends_count != 0) {
			int sum = 0;
			for (User u : this.friends) {
				sum += u.getPublic_metrics().getFollowing_count();
			}
			return sum / this.friends_count;
		} else {
			return 0;
		}
	}

	@Override
	public float friendsPercentageWithDescription() {
		if (this.friends_count != 0) {
			int friendsWithDescription = 0;
			for (User u : this.friends) {
				if (!u.getDescription().isBlank()) {
					friendsWithDescription++;
				}
			}
			return (float)friendsWithDescription / this.friends_count * 100;
		} else {
			return 0;
		}
	}

	@Override
	public int friendsTweetsAverageNumber() {
		if (this.friends_count != 0) {
			int sum = 0;
			for (User u : this.friends) {
				sum += u.getPublic_metrics().getTweet_count();
			}
			return sum / this.friends_count;
		} else {
			return 0;
		}
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
