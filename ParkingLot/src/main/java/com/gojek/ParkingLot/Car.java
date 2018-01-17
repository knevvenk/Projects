package com.gojek.ParkingLot;

/**
 * 
 * @author banve02
 *
 */
public class Car {
	
	String registrationNumber;
	String color;
	
	Car(String regNumber_, String color_){
		this.registrationNumber = regNumber_;
		this.color = color_;
	}
	public String getRegistrationNumber() {
		return registrationNumber;
	}
	public void setRegistrationNumber(String registrationNumber) {
		this.registrationNumber = registrationNumber;
	}
	public String getColor() {
		return color;
	}
	public void setColor(String color) {
		this.color = color;
	}
	
}
