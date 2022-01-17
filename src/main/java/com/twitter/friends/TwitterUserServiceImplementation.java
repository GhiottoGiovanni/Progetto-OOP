package com.twitter.friends;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.twitter.models.TwitterUser;
import com.twitter.models.User;
import com.twitter.APIcaller.Caller;
import com.twitter.exceptions.NegativeNumberException;
import com.twitter.exceptions.NotExistingAccountException;
import com.twitter.filters.friends.*;
import com.twitter.statistics.friends.*;

/**
 * <b>Classe</b> che implementa {@link TwitterUserService}
 * @author Giovanni Ghiotto
 * @author Mihail Bobeica
 * @version 1.0
 */

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
	public String getAllFriends(String username) {
		try {
			TwitterUser tu = initTwitterUser(username);
			ArrayNode data = Caller.OBJECT_MAPPER.createArrayNode();
			for (User u : tu.getFriends()) {
				data.add(Caller.OBJECT_MAPPER.convertValue(u, JsonNode.class));
			}
			return FilterUsers.structureData(data, tu.getFriends_count());
		} catch (NotExistingAccountException e) {
			e.toString();
			e.printStackTrace();
			return e.toString();
		}
	}

	@Override
	public String filter(String username, String word, Integer minTweets, Integer minFollowers) throws NegativeNumberException {
		if (word == null & minTweets == null & minFollowers == null) {
			return null;
		}
		if (minTweets != null) {
			if (minTweets.intValue() < 0) {
				throw new NegativeNumberException("min_tweets=" + minTweets.intValue());
			}
		}
		if (minFollowers != null) {
			if (minFollowers.intValue() < 0) {
				throw new NegativeNumberException("min_followers=" + minFollowers.intValue());
			}
		}
		try {
			TwitterUser tu = initTwitterUser(username);
			
			ArrayList<User> filteredUsers = tu.getFriends();
			
			if (word != null) {
				filteredUsers = new FilterByWordInDescription(filteredUsers).filter(word);
			}
			if (minTweets != null) {
				filteredUsers = new FilterTweetsNumber(filteredUsers).filter(minTweets.intValue());
			}
			if (minFollowers != null) {
				filteredUsers = new FilterFollowersNumber(filteredUsers).filter(minFollowers.intValue());
			}
			return FilterUsers.structureData(filteredUsers);
		} catch (NotExistingAccountException e) {
			e.toString();
			e.printStackTrace();
			return e.toString();
		}
	}
}

