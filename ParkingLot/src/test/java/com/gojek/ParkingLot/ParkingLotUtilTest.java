package com.gojek.ParkingLot;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import junit.framework.Assert;

public class ParkingLotUtilTest {
	
	private final ByteArrayOutputStream out = new ByteArrayOutputStream();
	
	ParkingLotUtil util = new ParkingLotUtil();
	
	@Before
	public void initUpStream(){
		System.setOut(new PrintStream(out));
	}
	
	@After
	public void closeUpStream(){
		System.setOut(null);
	}
	
	
	@Test
	public void userInputTest(){
		
		util.parseUserInput("status");
		Assert.assertEquals("Sorry, The Parking Lot is not created", out.toString().trim());
		
		out.reset();
		util.parseUserInput("create_parking_lot 2");
		Assert.assertEquals("Created a parking lot with 2 slots", out.toString().trim());
		
		out.reset();
		util.parseUserInput("status");
		Assert.assertEquals("Sorry, The Parking Lot is empty", out.toString().trim());
		
		out.reset();
		util.parseUserInput("park KA-01-HH-1234 White");
		Assert.assertEquals("Allocated slot number: 1", out.toString().trim());
		
		out.reset();
		util.parseUserInput("park KA-01-HH-9999 White");
		Assert.assertEquals("Allocated slot number: 2", out.toString().trim());
		
		out.reset();
		util.parseUserInput("leave 1");
		Assert.assertEquals("Slot number 1 is free", out.toString().trim());
		
		out.reset();
		util.parseUserInput("park KA-01-HH-1234 White");
		Assert.assertEquals("Allocated slot number: 1", out.toString().trim());
		
		out.reset();
		util.parseUserInput("park DL-12-AA-9999 White");
		Assert.assertEquals("Sorry, parking lot is full", out.toString().trim());
		
		out.reset();
		util.parseUserInput("registration_numbers_for_cars_with_colour White");
		Assert.assertEquals("KA-01-HH-1234, KA-01-HH-9999", out.toString().trim());
		
		out.reset();
		util.parseUserInput("slot_numbers_for_cars_with_colour White");
		Assert.assertEquals("1, 2", out.toString().trim());
		
		out.reset();
		util.parseUserInput("slot_number_for_registration_number KA-01-HH-1234");
		Assert.assertEquals("1", out.toString().trim());
		
		out.reset();
		util.parseUserInput("slot_number_for_registration_number KA-01-HH-3141");
		Assert.assertEquals("Not found", out.toString().trim());
	}
	
}
