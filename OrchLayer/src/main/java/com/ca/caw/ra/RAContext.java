package com.ca.caw.ra;

import com.arcot.riskfortAPI.AdditionalInputs;
import com.arcot.riskfortAPI.DeviceContext;
import com.arcot.riskfortAPI.LocationContext;
import com.arcot.riskfortAPI.TransactionContext;
import com.arcot.riskfortAPI.UserContext;

public class RAContext {
	
	private String userId;	
	private UserContext userContext;
	private TransactionContext transactionContext;
	private LocationContext locationContext;
	private DeviceContext deviceContext;
	private AdditionalInputs additionalInputs;
	
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public UserContext getUserContext() {
		return userContext;
	}
	public void setUserContext(UserContext userContext) {
		this.userContext = userContext;
	}
	public TransactionContext getTransactionContext() {
		return transactionContext;
	}
	public void setTransactionContext(TransactionContext transactionContext) {
		this.transactionContext = transactionContext;
	}
	public LocationContext getLocationContext() {
		return locationContext;
	}
	public void setLocationContext(LocationContext locationContext) {
		this.locationContext = locationContext;
	}
	public DeviceContext getDeviceContext() {
		return deviceContext;
	}
	public void setDeviceContext(DeviceContext deviceContext) {
		this.deviceContext = deviceContext;
	}
	public AdditionalInputs getAdditionalInputs() {
		return additionalInputs;
	}
	public void setAdditionalInputs(AdditionalInputs additionalInputs) {
		this.additionalInputs = additionalInputs;
	}
	
}
