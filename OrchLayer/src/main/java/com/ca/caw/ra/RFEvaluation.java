package com.ca.caw.ra;

import java.net.InetAddress;
import java.net.UnknownHostException;
import org.apache.log4j.Logger;

import com.arcot.riskfortAPI.AdditionalInputs;
import com.arcot.riskfortAPI.DeviceContext;
import com.arcot.riskfortAPI.LocationContext;
import com.arcot.riskfortAPI.PostEvaluateResponse;
import com.arcot.riskfortAPI.RFSDKException;
import com.arcot.riskfortAPI.RiskAssessment;
import com.arcot.riskfortAPI.RiskException;
import com.arcot.riskfortAPI.RiskFactory;
import com.arcot.riskfortAPI.RiskXActionAPI;
import com.arcot.riskfortAPI.TransactionContext;
import com.arcot.riskfortAPI.UserContext;
import com.ca.caw.ol.OrchLayerResource;
import com.ca.caw.ol.RiskEvalRequest;
import com.ca.caw.ol.RiskEvalResponse;
import com.ca.caw.util.OrchLayerUtils;

public class RFEvaluation {

	private static RiskXActionAPI riskXActionAPI = null;
	private static boolean riskInit = false;
	private static Logger logger = Logger.getLogger(OrchLayerResource.class);

	public static RiskAssessment riskEval(RiskEvalRequest req){
		
		RiskAssessment riskAssessmentReuturnObj = null;
		RAContext raContext = buildRAContext(req);		
		try {
			if(!riskInit)
			{	
				riskInit = OrchLayerUtils.initRASdk();
			}
			riskXActionAPI = RiskFactory.getRiskXActionAPI();
			if(riskXActionAPI == null){
				logger.info("RiskFort API initilization failed");
				return null;
			}
			String userCallerId = req.getCustomerId();
			logger.info("Invoking the Evaluate Risk");
			riskAssessmentReuturnObj = riskXActionAPI.evaluateRisk(userCallerId, raContext.getDeviceContext(), raContext.getLocationContext(),
						raContext.getUserContext(), raContext.getTransactionContext(), raContext.getAdditionalInputs());
			logger.info("Response received from Evalute Risk"); 
			
		} catch (RiskException e) {
			logger.info("Exception while evaluating Risk  :: "+e.getMessage());
		} catch (RFSDKException e) {
			logger.info("Exception while evaluating Risk :: "+e.getMessage());
		} catch (Exception e){
			logger.info("Exception while evaluating Risk :: "+e.getMessage());
		}		
		return riskAssessmentReuturnObj;
	}
	
	public static RiskEvalResponse postEval(RiskEvalRequest req, RiskAssessment riskAssessment, RiskEvalResponse res){
		RiskEvalResponse response = new RiskEvalResponse();
		PostEvaluateResponse postEvaluateResponseObj = null;
		try {
			if(!riskInit)
			{	
				riskInit = OrchLayerUtils.initRASdk();
			}
			riskXActionAPI = RiskFactory.getRiskXActionAPI();
			if(riskXActionAPI == null){
				logger.info("RiskFort API initilization failed");
				return null;
			}
			String userCallerId = req.getCustomerId();
			AdditionalInputs additionalInputs = new AdditionalInputs();
			
			boolean secAuth = false;
			if(RAXFortConstants.STATUS_SUCCESS.equals(res.getStatus())){
				additionalInputs.put("TXNSTATUS", "Y");
				secAuth = true;
			}
			logger.info("Invoking the Post Evaluate");
			postEvaluateResponseObj = riskXActionAPI.postEvaluate(userCallerId, riskAssessment, secAuth,
					userCallerId, additionalInputs);
			logger.info("Response received from Post Evalute");
			
		} catch (RiskException e) {
			logger.info("Exception while doing the postEval  :: "+e.getMessage());
		} catch (RFSDKException e) {
			logger.info("Exception while doing the postEval  :: "+e.getMessage());
		}
		
		if(postEvaluateResponseObj.isAllowAdvised())
			response.setStatus("SUCCESS");
		else{
			logger.info("Post eval returned Failure");
			response.setStatus("FAILURE");
			response.setMessage("Authentication Failed");
		}
		
		return response;
	}
	
	private static RAContext buildRAContext(RiskEvalRequest req) {
		RAContext raContext = new RAContext();
		
		UserContext userContext = prepareUserContext(req);
		raContext.setUserContext(userContext);
		
		TransactionContext transactionContext = prepareTransactionContext(req);
		raContext.setTransactionContext(transactionContext);
		
		LocationContext locationContext = prepateLocationContext();
		raContext.setLocationContext(locationContext);
		
		DeviceContext deviceContext = prepateDeviceContext(req);
		raContext.setDeviceContext(deviceContext);
		
		AdditionalInputs additionalInputs = prepareAdditionalInputs(req);
		raContext.setAdditionalInputs(additionalInputs);		
		
		return raContext;
	}

	private static AdditionalInputs prepareAdditionalInputs(RiskEvalRequest req) {
		AdditionalInputs additionalInputs = new AdditionalInputs();
		
		if(RAXFortConstants.FUNDSTRANSFER_EVENT.equals(req.getEvent()))
			additionalInputs.put("AMOUNT", req.getAmount());
		
		additionalInputs.put("ACTION", req.getEvent());
		
		return additionalInputs;
	}

	private static DeviceContext prepateDeviceContext(RiskEvalRequest req) {
		DeviceContext deviceContext = new DeviceContext();		
		String deviceDataStr = OrchLayerUtils.parseDeviceDataToString(req.getDeviceData());		
		deviceContext.buildDeviceSignature(deviceDataStr, null, null);
		deviceContext.setDeviceID("HTTP_COOKIE", null);
		deviceContext.setMFPShortForm("");		
		return deviceContext;
	}

	private static LocationContext prepateLocationContext() {
		LocationContext locationContext = new LocationContext();
		try {
			locationContext.setIpAddress(InetAddress.getLocalHost());
		} catch (UnknownHostException e) {
			logger.info("Host Exception :: "+e.getMessage());
		}
		
		return locationContext;
	}

	private static TransactionContext prepareTransactionContext(RiskEvalRequest req) {
		TransactionContext transactionContext = new TransactionContext();
		transactionContext.setAction(req.getEvent());
		transactionContext.setChannel("DEFAULT");
		return transactionContext;
	}

	private static UserContext prepareUserContext(RiskEvalRequest req) {
		UserContext userContext = new UserContext();
		userContext.setUserId(req.getCustomerId());
		userContext.setOrg(req.getOrganizationName());
		
		return userContext;
	}

	
}
