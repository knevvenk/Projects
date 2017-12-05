package com.ca.caw.pn;

import java.io.IOException;
import java.util.Properties;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Form;
import javax.ws.rs.core.Response;

import org.apache.log4j.Logger;
import org.codehaus.jackson.annotate.JsonAutoDetect;
import org.codehaus.jackson.map.ObjectMapper;
import org.glassfish.jersey.client.ClientProperties;
import org.json.JSONException;
import org.json.JSONObject;

import com.ca.caw.ol.RiskEvalRequest;
import com.ca.caw.ra.RAXFortConstants;
import com.ca.caw.util.OrchLayerUtils;
import com.ca.caw.util.SSLConnections;

public class PushNotificationClient {
	
	private WebTarget target;
    private Client client;
    private Response response;
    
    private static Logger logger = Logger.getLogger(PushNotificationClient.class);
    
	public PushNotificationClientResponse sendPushNotification(RiskEvalRequest req){
		
		String requestJson = prepareRequestJsonFromReq(req);
		
//		config = new ClientConfig();
        client = SSLConnections.allowSSLConnections();        
        client.property(ClientProperties.CONNECT_TIMEOUT, 50000);
        client.property(ClientProperties.READ_TIMEOUT,    120000);
        
        Properties prop = OrchLayerUtils.propertyLoader();
        String serverUrl = prop.getProperty("MPNSERVERURL");
                
        Form form = new Form();
        form.param("requestJson", requestJson);
        target = client.target(serverUrl);
        try{
        	logger.info("Invoking the Push Notification service");
        	response = target.request().post(Entity.form(form), Response.class);
        } catch(Exception e){
        	logger.info("Exception while sending the notification :: "+e.getMessage());
        	return null;
        }
        
        String responseStr = response.readEntity(String.class);
        if(responseStr == null)
        	return null;
        
        logger.info("Received response from the Push Notification service");
        JSONObject responseJson = null;
		try {
			responseJson = new JSONObject(responseStr);
		} catch (JSONException e) {
			logger.info("Invalid response type returned from the Push Notification service :: "+e.getMessage());
			return null;
		} 
        
        PushNotificationClientResponse pnRes = new PushNotificationClientResponse();
        pnRes.setResponseCode(responseJson.get("ResponseCode").toString());
        pnRes.setErrorDescription(responseJson.get("ErrorDescription").toString());
        
        return pnRes;
        
	}

	private String prepareRequestJsonFromReq(RiskEvalRequest req) {
		String requestJson = null;
		PushNotificationClientRequest pnReq = new PushNotificationClientRequest();
		pnReq.setUserId(req.getCustomerId());
		pnReq.setOrgName(req.getOrganizationName());
		pnReq.setRequestType(RAXFortConstants.MPN_PUSH_VERIFY);
		pnReq.setPushBodyMessage(req.getReqMessage());
		
		ObjectMapper mapper = new ObjectMapper();
		mapper.setVisibilityChecker(mapper.getSerializationConfig().getDefaultVisibilityChecker()
                .withFieldVisibility(JsonAutoDetect.Visibility.ANY)
                .withGetterVisibility(JsonAutoDetect.Visibility.NONE)
                .withSetterVisibility(JsonAutoDetect.Visibility.NONE)
                .withCreatorVisibility(JsonAutoDetect.Visibility.NONE));
		
		try {
			requestJson = mapper.writeValueAsString(pnReq);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return requestJson;
	}
}
