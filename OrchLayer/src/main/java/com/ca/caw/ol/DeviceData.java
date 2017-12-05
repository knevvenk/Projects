package com.ca.caw.ol;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

import com.fasterxml.jackson.annotation.JsonProperty;
@JsonIgnoreProperties(ignoreUnknown=true)
public class DeviceData {
	@JsonProperty("DV")
	String DV;	
	DD DD;	
	DPNA DPNA;
	
	public DeviceData(){		
	}
	
	@JsonProperty("DV")
	public String getDV() {
		return DV;
	}
	@JsonProperty("DV")
	public void setDV(String dV) {
		DV = dV;
	}
	
	public DD getDD() {
		return DD;
	}
	public void setDD(DD DD) {
		this.DD = DD;
	}
	
	public DPNA getDPNA() {
		return DPNA;
	}
	public void setDPNA(DPNA DPNA) {
		this.DPNA = DPNA;
	}
	
	
	
}
