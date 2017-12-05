package com.ca.caw.logger;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

public class OrchLayerLogger {
	
	private static Logger logger;
	private static String LOG4J_FILENAME = "OrchLayerLog4j.properties";

	public static void initLogger() {
		if(logger == null){
			 logger = Logger.getLogger(OrchLayerLogger.class);
			 String arcotHomePath = System.getenv("OL_HOME")+"/conf/"+LOG4J_FILENAME;
			 System.setProperty("OrchLayerLog", System.getenv("OL_HOME")+"/logs");
			 PropertyConfigurator.configure(arcotHomePath);
			 logger.debug("LOG4J Property file cofigured to : " + arcotHomePath);
			 logger.info("Service logger initialized successfully");
		}
	}
		
}
