package com.ca.caw.argus;

import java.util.Properties;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Form;
import javax.ws.rs.core.Response;

import org.apache.log4j.Logger;
import org.glassfish.jersey.client.ClientProperties;
import org.json.JSONException;
import org.json.JSONObject;

import com.ca.caw.ol.DeviceData;
import com.ca.caw.util.OrchLayerUtils;
import com.ca.caw.util.SSLConnections;

public class ResetDeviceReputation {
	
	private WebTarget target;
    private Client client;
    private Response response;
    
    private static Logger logger = Logger.getLogger(ResetDeviceReputation.class);
    
	public DeviceReputationResponse resetDeviceReputation(DeviceData deviceData){
		
		String deviceDataStr = OrchLayerUtils.parseDeviceDataToString(deviceData);
		
//		config = new ClientConfig();
//        client = ClientBuilder.newClient(config);
		client = SSLConnections.allowSSLConnections();
        client.property(ClientProperties.CONNECT_TIMEOUT, 50000);
        client.property(ClientProperties.READ_TIMEOUT,    120000);
        
        Properties prop = OrchLayerUtils.propertyLoader();
        String serverUrl = prop.getProperty("ARGUSSERVERURL");
                
        Form form = new Form();
        form.param("device_sig", deviceDataStr);
        target = client.target(serverUrl);
        try{
        	logger.info("Invoking the Device Reputation Service");
        	response = target.request().post(Entity.form(form), Response.class);
        } catch(Exception e){
        	logger.info("Device Reputation service failed :: "+e.getMessage());
        	return null;
        }
        
        String responseStr = response.readEntity(String.class);
        if(responseStr == null)
        	return null;
        
        logger.info("Received response from the Device Reputation service");
        JSONObject responseJson = null;
		try {
			responseJson = new JSONObject(responseStr);
		} catch (JSONException e) {
			logger.info("Invalid response type returned from the Device Reputation service :: "+e.getMessage());
			return null;
		}
		
		DeviceReputationResponse drResponse = new DeviceReputationResponse();
		drResponse.setResponseCode(responseJson.getInt("ResponseCode"));
		drResponse.setResponseMessage(responseJson.getString("ResponseMessage"));
		return drResponse;
	}

}
