package org.dynamik;

import org.dynamik.demo.CarRentalDriver;

public class Main {

    private static CarRentalDriver carRentalDriver = new CarRentalDriver();
    public static void main(String[] args) {
        System.out.println("Car Rental Application!");

        carRentalDriver.demo();
    }
}