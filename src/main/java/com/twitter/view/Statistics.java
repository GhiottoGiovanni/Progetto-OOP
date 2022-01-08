package com.twitter.view;

public interface Statistics {
	// required Start
	int friendsFollowersAverageNumber();
	
	float friendsPercentageWithDescription();
	// required End
	
	// optional Start
	int friendsFollowingAverageNumber();
	
	int friendsTweetsAverageNumber();
	// optional End
}
