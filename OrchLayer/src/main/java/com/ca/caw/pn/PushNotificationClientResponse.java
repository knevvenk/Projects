package com.ca.caw.pn;

import javax.xml.bind.annotation.XmlElement;

public class PushNotificationClientResponse {
	String status;
	
	@XmlElement(name="ErrorDescription")
	String errorDescription;
	@XmlElement(name="ResponseCode")
	String responseCode;
	
	public String getErrorDescription() {
		return errorDescription;
	}

	public void setErrorDescription(String errorDescription) {
		this.errorDescription = errorDescription;
	}

	public String getResponseCode() {
		return responseCode;
	}

	public void setResponseCode(String responseCode) {
		this.responseCode = responseCode;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
}
