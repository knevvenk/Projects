package com.ca.caw.util;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Base64;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.annotate.JsonAutoDetect;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;

import com.arcot.riskfortAPI.RFSDKException;
import com.arcot.riskfortAPI.RiskFactory;
import com.ca.caw.ol.DeviceData;
import com.ca.caw.ol.RiskEvalRequest;

public class OrchLayerUtils {
	
	private static Logger logger = Logger.getLogger(OrchLayerUtils.class);
	
	public static boolean initRASdk() {
		logger.info("Initializing the RA sdk");
		String properties = null; 
		try {
			RiskFactory.initialize(properties);
		} catch (IOException | RFSDKException e) {
			logger.info("SDK intialization failed :: "+e.getMessage());
			return false;
		}
		return true;
	}	
	
	public static Properties propertyLoader() {
		Properties prop = new Properties();
		String confPath = System.getenv("OL_HOME")+"/conf/";
		FileInputStream input;
		try {
			input = new FileInputStream(confPath+"configurations.properties");
			prop.load(input);
			input.close();
			
		} catch (FileNotFoundException e) {
			logger.info("Exception while reading the configuration.properties file :: "+e.getMessage());
		} catch (IOException e) {
			logger.info("Exception while reading the configuration.properties file :: "+e.getMessage());
		}
		
      return prop;
	}
	
	public static String parseDeviceDataToString(DeviceData deviceData){
		
		ObjectMapper mapper = new ObjectMapper();
		mapper.setVisibilityChecker(mapper.getSerializationConfig().getDefaultVisibilityChecker()
                .withFieldVisibility(JsonAutoDetect.Visibility.ANY)
                .withGetterVisibility(JsonAutoDetect.Visibility.NONE)
                .withSetterVisibility(JsonAutoDetect.Visibility.NONE)
                .withCreatorVisibility(JsonAutoDetect.Visibility.NONE));
		mapper.setSerializationInclusion(Inclusion.NON_NULL);

		
		String deviceDataStr = "{\"VERSION\":\"3.0\",\"MFP\":";
		try {
			deviceDataStr += mapper.writeValueAsString(deviceData);
		} catch (JsonGenerationException e) {
			logger.info("Exception while converting the object to json string  :: "+e.getMessage());
		} catch (JsonMappingException e) {
			logger.info("Exception while converting the object to json string  :: "+e.getMessage());
		} catch (IOException e) {
			logger.info("Exception while converting the object to json string  :: "+e.getMessage());
		}
		deviceDataStr += "}";
		
		return deviceDataStr;
	}
	
	public static RiskEvalRequest convertEnStringToRequestObject(String encodedJsonRequest){
		
		Base64.Decoder decoder = Base64.getDecoder();
		String decodedJsonRequest = new String(decoder.decode(encodedJsonRequest));
	
		ObjectMapper mapper = new ObjectMapper();
		mapper.setVisibilityChecker(mapper.getSerializationConfig().getDefaultVisibilityChecker()
                .withFieldVisibility(JsonAutoDetect.Visibility.ANY)
                .withGetterVisibility(JsonAutoDetect.Visibility.NONE)
                .withSetterVisibility(JsonAutoDetect.Visibility.NONE)
                .withCreatorVisibility(JsonAutoDetect.Visibility.NONE));
		
		RiskEvalRequest req = null;
		try {
			req = mapper.readValue(decodedJsonRequest, RiskEvalRequest.class);
		} catch (IOException e) {
			logger.debug("Decoded Json String :: "+decodedJsonRequest);
			logger.info("Unable to create the request object from decoded request string");			
		}
		
		return req;
	}
}
