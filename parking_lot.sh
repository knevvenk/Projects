#!/bin/bash
cd ParkingLot
mvn install
java -jar target/ParkingLot-0.0.1-SNAPSHOT.jar $1
