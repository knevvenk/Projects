package com.ca.caw.argus;

import javax.xml.bind.annotation.XmlElement;

public class DeviceReputationResponse {
	
	@XmlElement(name="ResponseCode")
	int responseCode;
	
	@XmlElement(name="ResponseMessage")
	String responseMessage;

	public int getResponseCode() {
		return responseCode;
	}

	public void setResponseCode(int responseCode) {
		this.responseCode = responseCode;
	}

	public String getResponseMessage() {
		return responseMessage;
	}

	public void setResponseMessage(String responseMessage) {
		this.responseMessage = responseMessage;
	}
	
	
}
