package com.twitter.models;

public class User extends BasicUser{
	private String description;
	private String location;
	private boolean verified;
	private PublicMetrics public_metrics;
	
	// TODO add constructors if needed
	
	public String getDescription() {
		return description;
	}
	
	public void setDescription(String description) {
		this.description = description;
	}
	
	public String getLocation() {
		return location;
	}
	
	public void setLocation(String location) {
		this.location = location;
	}
	
	public boolean isVerified() {
		return verified;
	}
	
	public void setVerified(boolean verified) {
		this.verified = verified;
	}
	
	public PublicMetrics getPublic_metrics() {
		return public_metrics;
	}
	
	public void setPublic_metrics(PublicMetrics public_metrics) {
		this.public_metrics = public_metrics;
	}
}
