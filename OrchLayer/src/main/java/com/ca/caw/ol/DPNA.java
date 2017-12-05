package com.ca.caw.ol;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown=true)
public class DPNA {
	public String C011;
	public String C012;
    
    public DPNA(){
    	
    }
	public String getC011() {
		return C011;
	}
	public void setC011(String c011) {
		C011 = c011;
	}
	public String getC012() {
		return C012;
	}
	public void setC012(String c012) {
		C012 = c012;
	}
}
