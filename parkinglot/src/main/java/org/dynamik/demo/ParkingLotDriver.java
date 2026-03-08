package org.dynamik.demo;

import org.dynamik.constants.BookingStatus;
import org.dynamik.constants.FareType;
import org.dynamik.constants.VehicleType;
import org.dynamik.model.*;
import org.dynamik.service.*;

public class ParkingLotDriver {
    private static ParkingLotDriver instance;
    private final IParkingLotService parkingLotService;
    private final VehicleService vehicleService;
    private final ParkingSpotService parkingSpotService;
    private final LevelService levelService;

    private ParkingLotDriver() {
        this.parkingLotService = ParkingLotService.getInstance();
        this.vehicleService = new VehicleService();
        this.parkingSpotService = new ParkingSpotService();
        this.levelService = new LevelService();
    }

    public static synchronized ParkingLotDriver getInstance() {
        if (instance == null) {
            instance = new ParkingLotDriver();
        }
        return instance;
    }

    public void demo() {
        initializeParkingLot();
        demonstrateParkingSystem();
    }

    public void initializeParkingLot() {
        // Create levels
        Level level1 = new Level();
        level1.setFloor(1L);
        levelService.save(level1);

        Level level2 = new Level();
        level2.setFloor(2L);
        levelService.save(level2);

        // Create parking spots
        createParkingSpots(level1.getId(), "A1-A10", VehicleType.BIKE);
        createParkingSpots(level1.getId(), "B1-B15", VehicleType.CAR);
        createParkingSpots(level2.getId(), "C1-C5", VehicleType.TRUCK);

        System.out.println("Parking Lot Initialized!");
        System.out.println("Level 1: 10 bike spots, 15 car spots");
        System.out.println("Level 2: 5 truck spots");
    }

    private void createParkingSpots(String levelId, String spotRange, VehicleType vehicleType) {
        String[] parts = spotRange.split("-");
        String prefix = parts[0].replaceAll("[0-9]", "");
        int start = Integer.parseInt(parts[0].replaceAll("[^0-9]", ""));
        int end = Integer.parseInt(parts[1].replaceAll("[^0-9]", ""));

        for (int i = start; i <= end; i++) {
            ParkingSpot spot = new ParkingSpot();
            spot.setNumber(prefix + i);
            spot.setLevelId(levelId);
            spot.setVehicleType(vehicleType);
            spot.setBookingStatus(BookingStatus.AVAILABLE);
            parkingSpotService.save(spot);
        }
    }

    public void demonstrateParkingSystem() {
        System.out.println("\n=== PARKING LOT DEMO ===");

        // Create vehicles
        Vehicle bike = new Bike("KA-01-1234");
        Vehicle car = new Car("KA-02-5678");
        Vehicle truck = new Truck("KA-03-9999");

        vehicleService.save(bike);
        vehicleService.save(car);
        vehicleService.save(truck);

        // Show available spots
        System.out.println("\n1. Available parking spots:");
        showAvailableSpots(bike);
        showAvailableSpots(car);
        showAvailableSpots(truck);

        // Park vehicles
        System.out.println("\n2. Parking vehicles:");
        parkVehicle(bike, FareType.HOURLY_RATE);
        parkVehicle(car, FareType.HOURLY_RATE);
        parkVehicle(truck, FareType.HOURLY_RATE);

        // Try to park another bike (should show reduced availability)
        Vehicle bike2 = new Bike("KA-01-4321");
        vehicleService.save(bike2);
        System.out.println("\n3. After parking, available bike spots:");
        showAvailableSpots(bike2);

        // Exit vehicles and calculate fare
        System.out.println("\n4. Vehicle exit and fare calculation:");
        // Note: In a real scenario, we'd need to store ticket IDs to exit vehicles
        // For demo purposes, showing the process
    }

    private void showAvailableSpots(Vehicle vehicle) {
        var availableSpots = parkingLotService.getAvailableParkingSpots(vehicle);
        System.out.println(vehicle.getVehicleType() + " - Available spots: " + availableSpots.size());
        availableSpots.forEach(spot -> System.out.println("  Spot " + spot.getNumber() + " on Level " + spot.getLevelId()));
    }

    private void parkVehicle(Vehicle vehicle, FareType fareType) {
        var availableSpots = parkingLotService.getAvailableParkingSpots(vehicle);
        if (!availableSpots.isEmpty()) {
            ParkingSpot spot = availableSpots.get(0);
            Ticket ticket = parkingLotService.bookParkingSpot(spot, vehicle.getId(), fareType);
            System.out.println("Parked " + vehicle.getVehicleType() + " (" + vehicle.getLicensePlate() + 
                             ") at spot " + spot.getNumber() + ". Ticket ID: " + ticket.getId());
        } else {
            System.out.println("No available spots for " + vehicle.getVehicleType());
        }
    }

    }
