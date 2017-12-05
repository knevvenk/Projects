package com.ca.caw.ol;


import javax.xml.bind.annotation.XmlElement;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown=true)
public class RiskEvalRequest {
	
	@XmlElement(name = "app_transaction_id")
	public String appTransactionId;	
	String version;	
	String event;
	String language;
	String customerId;
	DeviceData deviceData;
	String organizationName;
	String fromAccount;
	String toAccount;
	String description;
	String amount;
	String currency;
	String communication;
	String accountNo;
	String accountName;
	String accountType;
	String ifscCode;
	String email;
	String reqMessage;
	
	public String getReqMessage() {
		return reqMessage;
	}
	public void setReqMessage(String reqMessage) {
		this.reqMessage = reqMessage;
	}
	public String getAccountNo() {
		return accountNo;
	}
	public void setAccountNo(String accountNo) {
		this.accountNo = accountNo;
	}
	public String getAccountName() {
		return accountName;
	}
	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}
	public String getAccountType() {
		return accountType;
	}
	public void setAccountType(String accountType) {
		this.accountType = accountType;
	}
	public String getIfscCode() {
		return ifscCode;
	}
	public void setIfscCode(String ifscCode) {
		this.ifscCode = ifscCode;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getFromAccount() {
		return fromAccount;
	}
	public void setFromAccount(String fromAccount) {
		this.fromAccount = fromAccount;
	}
	public String getToAccount() {
		return toAccount;
	}
	public void setToAccount(String toAccount) {
		this.toAccount = toAccount;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getAmount() {
		return amount;
	}
	public void setAmount(String amount) {
		this.amount = amount;
	}
	public String getCurrency() {
		return currency;
	}
	public void setCurrency(String currency) {
		this.currency = currency;
	}
	public String getCommunication() {
		return communication;
	}
	public void setCommunication(String communication) {
		this.communication = communication;
	}
	
	public String getVersion() {
		return version;
	}
	public void setVersion(String version) {
		this.version = version;
	}
	public String getOrganizationName() {
		return organizationName;
	}
	public void setOrganizationName(String organizationName) {
		this.organizationName = organizationName;
	}
	
	public String getAppTransactionId() {
		return appTransactionId;
	}
	public void setAppTransactionId(String appTransactionId) {
		this.appTransactionId = appTransactionId;
	}
	public String getEvent() {
		return event;
	}
	public void setEvent(String event) {
		this.event = event;
	}
	public String getLanguage() {
		return language;
	}
	public void setLanguage(String language) {
		this.language = language;
	}
	public String getCustomerId() {
		return customerId;
	}
	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}
	public DeviceData getDeviceData() {
		return deviceData;
	}
	public void setDeviceData(DeviceData deviceData) {
		this.deviceData = deviceData;
	}
	
}
