package com.twitter.models;

/**
 * <b>Classe</b> contenente informazioni riguardanti l'attività di un utente.
 * @author Giovanni Ghiotto
 * @author Mihail Bobeica
 * @version 1.0
 */

public class PublicMetrics {
	private int followers_count;
	private int following_count;
	private int tweet_count;
	private int listed_count;

	// TODO add constructors if needed
	
	public int getFollowers_count() {
		return followers_count;
	}
	
	public void setFollowers_count(int followers_count) {
		this.followers_count = followers_count;
	}
	
	public int getFollowing_count() {
		return following_count;
	}
	
	public void setFollowing_count(int following_count) {
		this.following_count = following_count;
	}
	
	public int getTweet_count() {
		return tweet_count;
	}
	
	public void setTweet_count(int tweet_count) {
		this.tweet_count = tweet_count;
	}
	
	public int getListed_count() {
		return listed_count;
	}
	
	public void setListed_count(int listed_count) {
		this.listed_count = listed_count;
	}
}
