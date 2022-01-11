package com.twitter.friends;

import java.util.List;

public interface TwitterUserService {
	
	String getStatistics(String username);
	
	String filterByWordInDescription(String username, String word);
	
	String filterIsYourFriend(String username, List<String> friendsNames);
	
}
