package com.gojek.ParkingLot;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * 
 * @author banve02
 * 
 * This has methods related to ParkingLot. This class is singleton class so that it can be instantiated only once.
 * 
 */
public class ParkingLot {
	
	private static ParkingLot INSTANCE = null;
	
	static int SLOTS_SIZE = 0;
	static List<Integer> availableSlots;
	
	TreeMap<Integer, Car> carsMap = new TreeMap<Integer, Car>(); //Store Car's Object in given Slot
	Map<String, List<Integer>> slotsPerColor = new HashMap<String, List<Integer>>(); // Store list of Slots per color
	
	private ParkingLot(int size){
		SLOTS_SIZE = size;
		availableSlots = new ArrayList<Integer>();
		
		for(int i = 1; i <= SLOTS_SIZE ; i++)
			availableSlots.add(i);		
	}
	
	/**
	 * Creates the instance of ParkingLot and also creates the ParkingLot space only once.
	 * @param size
	 * @return
	 */
	public static ParkingLot getInstance(int size){
		if( INSTANCE == null){
			synchronized (ParkingLot.class) {
				if( INSTANCE == null){
					INSTANCE = new ParkingLot(size);					
					System.out.println("Created a parking lot with "+size+" slots");
				}
			}
		}
		return INSTANCE;
	}
	
	/**
	 * Park the car in least available slot number.
	 * Add the car in cars map and also update the slot number in the slotsColors map
	 * @param regNumber
	 * @param color
	 */
	public void park(String regNumber, String color){
		if(SLOTS_SIZE ==0){
			System.out.println("Sorry, The Parking Lot is not created");
		}
		else if(SLOTS_SIZE == carsMap.size()){
			System.out.println("Sorry, parking lot is full");
		}
		else{
			Collections.sort(availableSlots);
			int slotNumber = availableSlots.get(0);
			Car car = new Car(regNumber, color);
			carsMap.put(slotNumber, car);
			
			if(slotsPerColor.containsKey(color)){
				List<Integer> slotsList = slotsPerColor.get(color);
				slotsList.add(slotNumber);
				slotsPerColor.remove(color);
				slotsPerColor.put(color, slotsList);				
			}
			else{
				List<Integer> slotsList = new ArrayList<Integer>();
				slotsList.add(slotNumber);
				slotsPerColor.put(color, slotsList);
			}
			
			System.out.println("Allocated slot number: "+slotNumber);
			availableSlots.remove(0);
		}
	}
	
	/**
	 * Removes the car object from the carsMap
	 * Updates the slotsColors map by removing the slotNumber
	 * 
	 * @param slotNumber
	 */
	public void leave(int slotNumber){
		if(SLOTS_SIZE ==0){
			System.out.println("Sorry, The Parking Lot is not created");
		}
		else if(carsMap.size()<1){
			System.out.println("Sorry, The Parking Lot is empty");
		}
		else if(slotNumber>SLOTS_SIZE){
			System.out.println("Sorry, Please enter valid SlotNumber");
		}
		else{
			Car car = carsMap.get(slotNumber);
			if(car != null){
				String color = car.getColor();
				if(slotsPerColor.containsKey(color)){
					List<Integer> slotsList = slotsPerColor.get(color);
					int index = slotsList.indexOf(slotNumber);
					slotsList.remove(index);
					slotsPerColor.remove(color);
					slotsPerColor.put(color, slotsList);				
				}
				
				carsMap.remove(slotNumber);
				availableSlots.add(slotNumber);
				System.out.println("Slot number "+slotNumber+" is free");
			}			
			else{
				System.out.println("Sorry, The SlotNumber is empty");
			}
		}
	}
	
	/**
	 * Show the list of Parked cars
	 */
	public void status(){
		if(SLOTS_SIZE ==0){
			System.out.println("Sorry, The Parking Lot is not created");
		}
		else if(carsMap.size()<1){
			System.out.println("Sorry, The Parking Lot is empty");
		}
		else{
			System.out.println("Slot No.\tRegistration No\tColour");
			for(Map.Entry<Integer, Car> entry: carsMap.entrySet()){
				Integer slotNumber = entry.getKey();
				Car car = entry.getValue();
				System.out.println(slotNumber+"\t"+car.getRegistrationNumber()+"\t"+car.getColor());
			}
		}
	}
	
	/**
	 * Get the list of Registration Numbers based on the color
	 * @param color
	 */
	public void getRegNumbersByColor(String color) {
		if(SLOTS_SIZE ==0){
			System.out.println("Sorry, The Parking Lot is not created");
		}
		else if(carsMap.size()<1){
			System.out.println("Sorry, The Parking Lot is empty");
		}
		else{
			if(slotsPerColor.containsKey(color)){
				List<Integer> slotsList = slotsPerColor.get(color);
				Collections.sort(slotsList);
				Iterator<Integer> it = slotsList.iterator();				
				while(it.hasNext()){
					Car car = carsMap.get(it.next());
					System.out.print(car.getRegistrationNumber());
					if(it.hasNext())
						System.out.print(", ");
				}
				System.out.println();
			}
			else{
				System.out.println("Sorry, There is no Car exists with given color");
			}
		}
	}
	
	/**
	 * Get the list of Slot Numbers based on the color
	 * @param color
	 */
	public void getSlotNumbersByColor(String color) {
		if(SLOTS_SIZE ==0){
			System.out.println("Sorry, The Parking Lot is not created");
		}
		else if(carsMap.size()<1){
			System.out.println("Sorry, The Parking Lot is empty");
		}
		else{
			if(slotsPerColor.containsKey(color)){
				List<Integer> slotsList = slotsPerColor.get(color);
				Collections.sort(slotsList);
				Iterator<Integer> it = slotsList.iterator();				
				while(it.hasNext()){					
					System.out.print(it.next());
					if(it.hasNext())
						System.out.print(", ");
				}
				System.out.println();
			}
			else{
				System.out.println("Sorry, There is no Car exists with given color");
			}
		}
		
	}
	
	/**
	 * Get the Slot Number based on Registration Number
	 * @param regNumber
	 */
	public void getSlotNumberByRegNumber(String regNumber) {
		if(SLOTS_SIZE ==0){
			System.out.println("Sorry, The Parking Lot is not created");
		}
		else if(carsMap.size()<1){
			System.out.println("Sorry, The Parking Lot is empty");
		}
		else{
			Integer slotNumber = -1;
			for(Map.Entry<Integer, Car> entry: carsMap.entrySet()){				
				Car car = entry.getValue();
				if(regNumber.equalsIgnoreCase(car.getRegistrationNumber()))
					slotNumber = entry.getKey();
			}
			if(slotNumber != -1)
				System.out.println(slotNumber);
			else
				System.out.println("Not found");
		}
	}
}
