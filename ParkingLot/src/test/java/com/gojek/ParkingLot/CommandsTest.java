package com.gojek.ParkingLot;

import org.junit.Test;

import junit.framework.Assert;

public class CommandsTest {

	@Test
	public void commandsTest(){
		Assert.assertEquals("create_parking_lot", Commands.valueOf("create_parking_lot").toString());
		Assert.assertEquals("status", Commands.valueOf("status").toString());
	}
	
}
