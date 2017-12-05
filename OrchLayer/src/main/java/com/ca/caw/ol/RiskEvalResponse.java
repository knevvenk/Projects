package com.ca.caw.ol;

import javax.xml.bind.annotation.XmlElement;

public class RiskEvalResponse {
	
	@XmlElement(name = "app_transaction_id")
	public String appTransactionId;
	
	@XmlElement(name = "ol_transaction_id")
	public String olTransactionId;
	
	String status;
	String appRequestId;
	int riskScore;
	String riskAdvice;
	String event;	
	String accountNumber;
	String accountName;
	String branch;
	String accountBalance;
	String accountType;	
	String[] beneficiary;
	String[] communicationDetails;
	String message;
		

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String[] getBeneficiary() {
		return beneficiary;
	}

	public void setBeneficiary(String[] beneficiary) {
		this.beneficiary = beneficiary;
	}

	public String[] getCommunicationDetails() {
		return communicationDetails;
	}

	public void setCommunicationDetails(String[] communicationDetails) {
		this.communicationDetails = communicationDetails;
	}

	public String getEvent() {
		return event;
	}

	public void setEvent(String event) {
		this.event = event;
	}
	
	public String getAppTransactionId() {
		return appTransactionId;
	}
	public void setAppTransactionId(String appTransactionId) {
		this.appTransactionId = appTransactionId;
	}

	public String getOlTransactionId() {
		return olTransactionId;
	}
	public void setOlTransactionId(String olTransactionId) {
		this.olTransactionId = olTransactionId;
	}

	public String getAccountNumber() {
		return accountNumber;
	}

	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}

	public String getAccountName() {
		return accountName;
	}

	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}

	public String getBranch() {
		return branch;
	}

	public void setBranch(String branch) {
		this.branch = branch;
	}

	public String getAccountBalance() {
		return accountBalance;
	}

	public void setAccountBalance(String accountBalance) {
		this.accountBalance = accountBalance;
	}

	public String getAccountType() {
		return accountType;
	}

	public void setAccountType(String accountType) {
		this.accountType = accountType;
	}

	public String getRiskAdvice() {
		return riskAdvice;
	}

	public void setRiskAdvice(String riskAdvice) {
		this.riskAdvice = riskAdvice;
	}


	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getAppRequestId() {
		return appRequestId;
	}

	public void setAppRequestId(String appRequestId) {
		this.appRequestId = appRequestId;
	}

	public int getRiskScore() {
		return riskScore;
	}

	public void setRiskScore(int riskScore) {
		this.riskScore = riskScore;
	}
	
}
