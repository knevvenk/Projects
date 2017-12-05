package com.ca.caw.pn;

public class PushNotificationClientRequest {
	
	String userId;
	String orgName;
	String requestType;
	String pushBodyMessage;
	
	public String getPushBodyMessage() {
		return pushBodyMessage;
	}
	public void setPushBodyMessage(String pushBodyMessage) {
		this.pushBodyMessage = pushBodyMessage;
	}
	public String getRequestType() {
		return requestType;
	}
	public void setRequestType(String requestType) {
		this.requestType = requestType;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getOrgName() {
		return orgName;
	}
	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}
	
}
