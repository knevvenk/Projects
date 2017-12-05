package com.ca.caw.ol;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.apache.log4j.Logger;

import com.arcot.riskfortAPI.RiskAssessment;
import com.ca.caw.argus.DeviceReputationResponse;
import com.ca.caw.argus.ResetDeviceReputation;
import com.ca.caw.logger.OrchLayerLogger;
import com.ca.caw.pn.PushNotificationClient;
import com.ca.caw.pn.PushNotificationClientResponse;
import com.ca.caw.ra.RAXFortConstants;
import com.ca.caw.ra.RFEvaluation;
import com.ca.caw.util.OrchLayerUtils;

@Path("")
public class OrchLayerResource {
	
	static {
		OrchLayerLogger.initLogger();		
	}
	private static Logger logger = Logger.getLogger(OrchLayerResource.class);

	@POST
	@Path("/dcsRiskEvalRequest")
	@Produces({MediaType.APPLICATION_JSON})
	
	public RiskEvalResponse dcsRiskEvalRequest(String encodedJsonRequest){
		
		logger.info("Request Recieved from the application");
		RiskEvalResponse res = null;
		RiskEvalRequest req = OrchLayerUtils.convertEnStringToRequestObject(encodedJsonRequest);
		RiskAssessment riskAssessment = RFEvaluation.riskEval(req);
		if(riskAssessment == null){
			return prepareErrorResponse("Connection Failure, Please try again after sometime");
		}
		String riskAdvice = riskAssessment.getRiskAdvice();
		logger.info("Received [ "+riskAdvice+" ] from the Evaluate Risk");
		if(RAXFortConstants.RA_ADVICE_INCREASEAUTH.equals(riskAdvice) || RAXFortConstants.RA_ADVICE_ALERT.equals(riskAdvice)){
			PushNotificationClient pnClient = new PushNotificationClient();
			PushNotificationClientResponse pnResponse = pnClient.sendPushNotification(req);
			res = prepareRiskEvalResponse(req, riskAssessment, pnResponse);
			// HACKING - If PushNotification sending fails or User is unable to approve the transaction then do not call the post eval
			if(pnResponse != null)
				RFEvaluation.postEval(req, riskAssessment, res);
		}
		else{
			res = prepareRiskEvalResponse(req, riskAssessment);
			RFEvaluation.postEval(req, riskAssessment, res);
		}		
		return res;
	}
	
	private RiskEvalResponse prepareErrorResponse(String message) {
		RiskEvalResponse res = new RiskEvalResponse();
		res.setStatus(RAXFortConstants.STATUS_FAILURE);
		res.setMessage(message);
		return res;
	}
	
	private RiskEvalResponse prepareRiskEvalResponse(RiskEvalRequest req, RiskAssessment riskAssessment,
			PushNotificationClientResponse pnResponse) {
		if(pnResponse == null){
			return prepareErrorResponse("Authentication Failure, Please contact Bank");
		}
		RiskEvalResponse res = new RiskEvalResponse();
		res.setAppTransactionId(req.getAppTransactionId());
		res.setRiskAdvice(riskAssessment.getRiskAdvice());
		res.setRiskScore(riskAssessment.getRiskScore());
		res.setOlTransactionId(riskAssessment.getTransactionId());
		
		if (RAXFortConstants.MPN_APPROVED.equals(pnResponse.getResponseCode())) {
			logger.info("User approved the notification from the device");
			prepareEventsResponse(res, req);
			
		} else {
			logger.info("User denied the notification from the device or User did not receive the notification");
			return prepareErrorResponse("Authentication Failed");			
		}
		return res;
	}

	private void prepareEventsResponse(RiskEvalResponse res, RiskEvalRequest req) {
		res.setStatus(RAXFortConstants.STATUS_SUCCESS);
		if (RAXFortConstants.LOGIN_EVENT.equals(req.getEvent()))
			prepareLoginEventResponse(res);
		if (RAXFortConstants.FUNDSTRANSFER_EVENT.equals(req.getEvent()))
			prepareFundsTransferEventResponse(req, res);
		if(RAXFortConstants.ADDBENEFICIARY_EVENT.equals(req.getEvent()))
			prepareAddBeneficiaryEventResponse(req, res);
		
	}

	private void prepareAddBeneficiaryEventResponse(RiskEvalRequest req, RiskEvalResponse res) {
		String message = "The Beneficiary Account Name "+req.getAccountName()+" added successfully";
		res.setMessage(message);
		
	}

	private void prepareLoginEventResponse(RiskEvalResponse res) {
		int index = (int)(Math.random() * 5);
		res.setAccountBalance(RAXFortConstants.accountBalance[index]);
		res.setAccountName(RAXFortConstants.accoutName[index]);
		res.setAccountNumber(RAXFortConstants.accountNumber[index]);
		res.setAccountType(RAXFortConstants.accountType[0]);
		res.setBranch(RAXFortConstants.branch[index]);
	}
	
	private void getFundTransferResponse(RiskEvalResponse res){		
		String[] beneficiary = new String[5];
		String[] communicationDetails = new String[5];
		for(int i =0; i<beneficiary.length; i++)
		{
			beneficiary[i] = RAXFortConstants.accoutName[i+5];
			communicationDetails[i] = RAXFortConstants.contactDetails[i];
		}
		res.setBeneficiary(beneficiary);
		res.setCommunicationDetails(communicationDetails);
	}
	
	private RiskEvalResponse prepareRiskEvalResponse(RiskEvalRequest req, RiskAssessment riskAssessment) {
		RiskEvalResponse res = new RiskEvalResponse();
		res.setAppTransactionId(req.getAppTransactionId());

		String riskAdvice = riskAssessment.getRiskAdvice();
		res.setRiskAdvice(riskAdvice);
		res.setRiskScore(riskAssessment.getRiskScore());
		if(RAXFortConstants.RA_ADVICE_ALLOW.equals(riskAdvice)){			
			prepareEventsResponse(res, req);			
		}
		else{
			logger.info("Risk Eval returned DENY for the device");
			return prepareErrorResponse("Authentication Failed");
		}
		
		return res;
	}

	private void prepareFundsTransferEventResponse(RiskEvalRequest req, RiskEvalResponse res) {
		String message = "Funds Transferred Successfully to "+req.getToAccount();
		res.setMessage(message);
	}

	@POST
	@Path("/dcsResetDeviceReputation")
	@Consumes({MediaType.APPLICATION_JSON})
	@Produces({MediaType.APPLICATION_JSON})
	
	public RiskEvalResponse dcsResetDeviceReputation(String encodedJsonRequest){
		ResetDeviceReputation resetDevice = new ResetDeviceReputation();
		RiskEvalRequest req = OrchLayerUtils.convertEnStringToRequestObject(encodedJsonRequest);
		DeviceReputationResponse drResponse = resetDevice.resetDeviceReputation(req.getDeviceData());
		if(drResponse == null)
			return prepareErrorResponse("Cache Reset is Failed, Please contact system administrator");
		
		RiskEvalResponse res = new RiskEvalResponse();
		if(RAXFortConstants.DEVICE_RESET_SUCCESSFUL == drResponse.getResponseCode()){
			logger.info("Cache Reset is Successful");
			res.setStatus(RAXFortConstants.STATUS_SUCCESS);
			res.setMessage("Cache Reset is Successful");
		}
		else {
			logger.info("Cache Reset is Failed");
			return prepareErrorResponse("Cache Reset if Failed");
		}
		return res;
	}

	@POST
	@Path("/dcsAccSummaryRequest")
	@Consumes({ MediaType.APPLICATION_JSON })
	@Produces({ MediaType.APPLICATION_JSON })

	public RiskEvalResponse dcsAccSummaryRequest() {
		RiskEvalResponse res = new RiskEvalResponse();
		res.setStatus(RAXFortConstants.STATUS_SUCCESS);
		prepareLoginEventResponse(res);
		return res;
	}

	@POST
	@Path("/dcsFundTransferRequest")
	@Consumes({ MediaType.APPLICATION_JSON })
	@Produces({ MediaType.APPLICATION_JSON })

	public RiskEvalResponse dcsFundTransferRequest() {
		RiskEvalResponse res = new RiskEvalResponse();
		res.setStatus(RAXFortConstants.STATUS_SUCCESS);
		getFundTransferResponse(res);
		return res;
	}

	@GET
	@Path("/init")
	@Produces(MediaType.TEXT_HTML)

	public String init() {
		String res = "Logger initialized successfully";
		return "<p>" + res + "</p>";
	}
	
}
