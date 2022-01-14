package com.twitter.friends;

import java.util.List;

import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.twitter.models.TwitterUser;
import com.twitter.APIcaller.Caller;
import com.twitter.exceptions.NotExistingAccountException;
import com.twitter.filters.friends.*;
import com.twitter.statistics.friends.*;

@Service
public class TwitterUserServiceImplementation implements TwitterUserService{
	
	private TwitterUser initTwitterUser(String username) throws NotExistingAccountException {
		String jsonTwitterUserData = Caller.jsonTwitterUserDataFromUsername(username);
		if (Caller.isNotNullOrEmptyString(jsonTwitterUserData)) {
			try {
				TwitterUser tu = Caller.OBJECT_MAPPER.readValue(jsonTwitterUserData, TwitterUser.class);
				tu.initFriends();
				return tu;
			} catch (JsonProcessingException e) {
				return null;
			}
		} else {
			throw new NotExistingAccountException(username);
		}
	}
	
	public String getStatistics(String username) {
		try {
			TwitterUser tu = initTwitterUser(username);
			ObjectNode root = Caller.OBJECT_MAPPER.createObjectNode();
			
			int fsan = new FollowersAverageNumber(tu.getFriends()).getIntValue();
			root.put("friends_followers_average_number", fsan);
			
			int fgan = new FollowingAverageNumber(tu.getFriends()).getIntValue();
			root.put("friends_following_average_number", fgan);
			
			int twan = new TweetsAverageNumber(tu.getFriends()).getIntValue();
			root.put("friends_tweets_average_number", twan);
			
			double prwd = new PercentageWithDescription(tu.getFriends()).getDoubleValue();
			root.put("friends_percentage_with_description", prwd);
			
			try {
				return Caller.OBJECT_MAPPER.writerWithDefaultPrettyPrinter().writeValueAsString(root);
			} catch (JsonProcessingException e) {
				e.toString();
				e.printStackTrace();
				return e.toString();
			}
		} catch (NotExistingAccountException e) {
			e.toString();
			e.printStackTrace();
			return e.toString();
		}
	}

	@Override
	public String filterByWordInDescription(String username, String word) {
		try {
			TwitterUser tu = initTwitterUser(username);
			return new FilterByWordInDescription(tu.getFriends()).filteredData(word);
		} catch (NotExistingAccountException e) {
			e.toString();
			e.printStackTrace();
			return e.toString();
		}
	}

	@Override
	public String filterIsYourFriend(String username, List<String> friendsNames) {
		try {
			TwitterUser tu = initTwitterUser(username);
			return new FilterIsYourFriend(tu.getFriends()).filteredData(friendsNames);
		} catch (NotExistingAccountException e) {
			e.toString();
			e.printStackTrace();
			return e.toString();
		}
	}

	@Override
	public String filterFollowersNumber(String username, int minFollowers) {
		try {
			TwitterUser tu = initTwitterUser(username);
			return new FilterFollowersNumber(tu.getFriends()).filteredData(minFollowers);
		} catch (NotExistingAccountException e) {
			e.toString();
			e.printStackTrace();
			return e.toString();
		}
	}

	@Override
	public String filterTweetsNumber(String username, int minTweets) {
		try {
			TwitterUser tu = initTwitterUser(username);
			return new FilterTweetsNumber(tu.getFriends()).filteredData(minTweets);
		} catch (NotExistingAccountException e) {
			e.toString();
			e.printStackTrace();
			return e.toString();
		}
	}
}

