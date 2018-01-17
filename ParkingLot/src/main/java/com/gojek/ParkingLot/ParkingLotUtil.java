package com.gojek.ParkingLot;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

/**
 * 
 * @author banve02
 * 
 * This has the methods to parse the user's console input.
 * 
 */
public class ParkingLotUtil {
	
	static ParkingLot parkingLot = null;
	
	public void parseUserInput(String userInput){
		
		String[] inputArray = userInput.split(" ");
		Commands command = null;
		try{
			command = Commands.valueOf(inputArray[0]);
		}catch (Exception e) {
			System.out.println("Sorry, Unable to parse your input..");
			System.out.println("Please use below commands");
			System.out.println("create_parking_lot, park, leave, status, registration_numbers_for_cars_with_colour, slot_numbers_for_cars_with_colour, slot_number_for_registration_number, exit");
			return;
		}
		
		switch (command) {
		
		case create_parking_lot:
			int size;
			try{
				if(2 == inputArray.length){
					size = Integer.parseInt(inputArray[1]);
					parkingLot = ParkingLot.getInstance(size);
				}
				else{
					System.out.println("Sorry, Please provide the valid ParkingLot size.");
				}
			}catch(NumberFormatException e){
				System.out.println("Sorry, The ParkingLot is cannot be created. Please provide the Numeric value for the size");
			}			
			break;

		case park:
			if(parkingLot == null)
				System.out.println("Sorry, The Parking Lot is not created");
			else if(3 != inputArray.length)
				System.out.println("Sorry, Please provide the valid Registration number and Color of the Car");
			else
				parkingLot.park(inputArray[1], inputArray[2]);
			break;

		case leave:
			if( parkingLot == null ){
				System.out.println("Sorry, The Parking Lot is not created");
			}
			else if(2 != inputArray.length)
				System.out.println("Sorry, Please provide the valid SlotNumber");
			else{
				int slotNumber;
				try{
					slotNumber = Integer.parseInt(inputArray[1]);
					parkingLot.leave(slotNumber);
				}catch(NumberFormatException e){
					System.out.println("Sorry, Please provide the valid slotNumber");
				}				
			}	
			break;

		case status:
			if( parkingLot != null)
				parkingLot.status();
			else
				System.out.println("Sorry, The Parking Lot is not created");
			break;

		case registration_numbers_for_cars_with_colour:
			if( parkingLot == null)
				System.out.println("Sorry, The Parking Lot is not created");
			else if(2 != inputArray.length)
				System.out.println("Sorry, Please provide the valid color value");
			else
				parkingLot.getRegNumbersByColor(inputArray[1]);
			break;

		case slot_numbers_for_cars_with_colour:
			if( parkingLot == null)
				System.out.println("Sorry, The Parking Lot is not created");
			else if(2 != inputArray.length)
				System.out.println("Sorry, Please provide the valid color value");
			else
				parkingLot.getSlotNumbersByColor(inputArray[1]);
			break;

		case slot_number_for_registration_number:
			if(parkingLot == null)
				System.out.println("Sorry, The Parking Lot is not created");
			else if(2 != inputArray.length)
				System.out.println("Sorry, Please provide valid Registration Number of the car");
			else
				parkingLot.getSlotNumberByRegNumber(inputArray[1]);
			break;		
		}
	}
	
	public void parseInputFile(String filePath){
		try {
			BufferedReader reader = new BufferedReader(new FileReader(filePath));
			String line = reader.readLine();
			while(line != null){
				parseUserInput(line.trim());
				line = reader.readLine();
			}
			reader.close();
				
		} catch (FileNotFoundException e) {
			
		} catch (IOException e) {
			
		}
	}
}
