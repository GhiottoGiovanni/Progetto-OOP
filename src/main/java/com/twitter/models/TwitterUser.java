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

import com.twitter.controller.Caller;

public class TwitterUser extends User{
	
	public final static String FRIENDS_STORAGE_DIR = "./src/main/resources/data/friends";
	private final static String FRIENDS_STORAGE_PATH = FRIENDS_STORAGE_DIR + "/";
	public final static boolean STORE_LOCALY = true;
	
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
		if (new File(getFriendsFileName()).exists() && STORE_LOCALY) {
			initFriendsFromLocal();
		} else {
			initFriendsFromAPIrequest();
		}
	}
	
	private void initFriendsFromLocal() {
		try {
			User[] friends = Caller.OBJECT_MAPPER.readValue(Paths.get(getFriendsFileName()).toFile(), User[].class);
			Collections.addAll(this.friends, friends);
			this.setFriends_count(this.friends.size());
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			// e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			// e.printStackTrace();
		}
	}

	private void initFriendsFromAPIrequest() {
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
		if (STORE_LOCALY) {
			try {
				Caller.OBJECT_MAPPER.writeValue(Paths.get(getFriendsFileName()).toFile(), this.friends);
				System.out.println("App: file temporaneo " + this.getUsername() + ".json salvato!");
			} catch (StreamWriteException e) {
				// TODO Auto-generated catch block
				// e.printStackTrace();
			} catch (DatabindException e) {
				// TODO Auto-generated catch block
				// e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				// e.printStackTrace();
			}
		}
	}
}
