package com.twitter.friends;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.twitter.models.TwitterUser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.twitter.controller.Caller;

@SpringBootApplication
public class FriendsApplication {

	public static void main(String[] args) {
		SpringApplication.run(FriendsApplication.class, args);
		
//		String username = "mihailbobeica";
//		try {
//			TwitterUser twitterUser = Caller.OBJECT_MAPPER.readValue(Caller.jsonTwitterUserDataFromUsername(username), TwitterUser.class);
//			twitterUser.initFriends();
//			
//			//System.out.println(twitterUser.isThisUserMyFriend("Mihail Bobeica"));
//			System.out.println("numero di amici: " + twitterUser.getFriends_count());
//			System.out.println("numero medio dei followers degli amici: " + twitterUser.friendsFollowersAverageNumber());
//			System.out.println("numero medio dei tweet degli amici: " + twitterUser.friendsTweetsAverageNumber());
//			System.out.println("numero medio delle persone che gli amici seguono: " + twitterUser.friendsFollowingAverageNumber());
//			System.out.println("percentuale degli amici con descrizione: " + twitterUser.friendsPercentageWithDescription());
//		} catch (JsonProcessingException e) {
//			e.printStackTrace();
//		}
	}
}
