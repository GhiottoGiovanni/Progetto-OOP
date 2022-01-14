package com.twitter.friends;

import java.util.List;

import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.twitter.models.TwitterUser;
import com.twitter.APIcaller.Caller;
import com.twitter.filters.friends.*;
import com.twitter.statistics.friends.*;

@Service
public class TwitterUserServiceImplementation implements TwitterUserService{
	
	private TwitterUser initTwitterUser(String username) {
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
			return null;
		}
	}
	
	public String getStatistics(String username) {
		TwitterUser tu = initTwitterUser(username);
		if (tu != null) {
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
		} else {
			return "Non esiste nessun account Twitter con il seguente nome identificativo: " + username;
		}
	}

	@Override
	public String filterByWordInDescription(String username, String word) {
		TwitterUser tu = initTwitterUser(username);
		if (tu != null) {
			return new FilterByWordInDescription(tu.getFriends()).filteredData(word);
		} else {
			return "Non esiste nessun account Twitter con il seguente nome identificativo: " + username;
		}
		
	}

	@Override
	public String filterIsYourFriend(String username, List<String> friendsNames) {
		TwitterUser tu = initTwitterUser(username);
		if (tu != null) {
			return new FilterIsYourFriend(tu.getFriends()).filteredData(friendsNames);
		} else {
			return "Non esiste nessun account Twitter con il seguente nome identificativo: " + username;
		}
	}

	@Override
	public String filterFollowersNumber(String username, int minFollowers) {
		TwitterUser tu = initTwitterUser(username);
		if (tu != null) {
			return new FilterFollowersNumber(tu.getFriends()).filteredData(minFollowers);
		} else {
			return "Non esiste nessun account Twitter con il seguente nome identificativo: " + username;
		}
	}

	@Override
	public String filterTweetsNumber(String username, int minTweets) {
		TwitterUser tu = initTwitterUser(username);
		if (tu != null) {
			return new FilterTweetsNumber(tu.getFriends()).filteredData(minTweets);
		} else {
			return "Non esiste nessun account Twitter con il seguente nome identificativo: " + username;
		}
	}

}

