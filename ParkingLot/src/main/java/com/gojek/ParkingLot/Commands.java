package com.gojek.ParkingLot;

/**
 * 
 * @author banve02
 *
 * List of constant command codes added here
 */
public enum Commands {
	
	create_parking_lot("create_parking_lot"), park("park"), leave("leave"), status("status"), registration_numbers_for_cars_with_colour("registration_numbers_for_cars_with_colour"), slot_numbers_for_cars_with_colour("slot_numbers_for_cars_with_colour"), slot_number_for_registration_number("slot_number_for_registration_number");
	
	String commandCode;
	Commands(String commandCode_){
		this.commandCode = commandCode_;
	}
	
}
