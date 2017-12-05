Below steps to be followed for deploying the OrchLayer.
1. Create OL_HOME system env variable.
2. Create logs folder under OL_HOME path.
3. Create conf folder under OL_HOME path.
4. Update the servers urls in OL_HOME/conf/configurations.properties file.
	a. MPNSERVERURL - This is the Mobile Push Notification server url.
	b. ARGUSSERVERURL - This is the Argus url for device reputation rest.
	NOTE: The above urls should have the complete url(this includes api's uri).
5. log4j properties can be updated in OL_HOME/conf/OrchLayerLog4j.properties file.
6. Update the RA server url in /properties/riskfort.risk-evaluation.properties file.
7. This Project requires below RA libs other than the Maven dependency libs.
	a. arcot_core.jar
	b. arcot_policy.jar
	c. arcot-pool.jar
	d. arcot-riskfort-evaluaterisk.jar
	e. arcot-riskfort-issuance.jar
	f. arcot-riskfort-mfp.jar
	