package com.twitter.friends;

import java.util.ArrayList;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Assertions;

import com.twitter.models.PublicMetrics;
import com.twitter.models.User;
import com.twitter.statistics.friends.FollowersAverageNumber;
import com.twitter.statistics.friends.PercentageWithDescription;

@SpringBootTest
class FriendsApplicationTests {

	@BeforeEach
	void contextLoads() {
	}
	
	@Test
	@DisplayName("Test metodo della statistiche FollowersAverageNumber")
	void followersAverageNumber() {
		Assertions.assertEquals(76, setPublicMetricsFollowersCount(100, 40, 75, 89).getIntValue());
	}
	
	@Test
	@DisplayName("Test metodo della statistica PercentageWithDescription")
	void percentageWithDescription() {
		Assertions.assertEquals(50d, setDescriptions("", "Hi!", "", "20").getDoubleValue());
	}
	
	private FollowersAverageNumber setPublicMetricsFollowersCount(int... followers_counts) {
		ArrayList<User> users = new ArrayList<User>();
		
		for (int fc : followers_counts) {
			User u = new User();
			PublicMetrics pm = new PublicMetrics();
			pm.setFollowers_count(fc);
			u.setPublic_metrics(pm);
			users.add(u);
		}
		
		return new FollowersAverageNumber(users);
	}
	
	private PercentageWithDescription setDescriptions(String... descriptions) {
		ArrayList<User> users = new ArrayList<User>();
		
		for (String d : descriptions) {
			User u = new User();
			u.setDescription(d);
			users.add(u);
		}
		
		return new PercentageWithDescription(users);
	}
}
