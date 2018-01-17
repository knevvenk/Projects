package com.gojek.ParkingLot;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * 
 * @author banve02
 *
 * 
 */
public class ApplicationRunner {

	public static void main(String[] args) {
		
		ParkingLotUtil util = new ParkingLotUtil();
		
		if(args.length == 0){
			System.out.println("Entered into Interactive Mode");
			System.out.println("Please Enter 'exit' to exit from Interactive Mode");
			
			while(true){
				BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
				String userInput;
				try {
					userInput = input.readLine().toString();
					
					if("exit".equalsIgnoreCase(userInput))
						break;
					
					else if(null == userInput || "".equals(userInput)){
						//do nothing
					}
					
					else{
						util.parseUserInput(userInput);
					}
						
				} catch (IOException e) {
					System.out.println("Sorry, Unable to parse your input..");
					System.out.println("Please use below commands");
					System.out.println("create_parking_lot, park, leave, status, registration_numbers_for_cars_with_colour, slot_numbers_for_cars_with_colour, slot_number_for_registration_number, exit");					
				}				
			}
		}
		else if(args.length == 1){
			util.parseInputFile(args[0]);
		}
		else{
			//do nothing
		}

	}

}
