package org.dynamik;

import org.dynamik.demo.ParkingLotDriver;

public class Main {
    public static void main(String[] args) {
        System.out.println("Parking Lot System!");

        ParkingLotDriver parkingLotDriver = ParkingLotDriver.getInstance();
        parkingLotDriver.demo();
    }
}