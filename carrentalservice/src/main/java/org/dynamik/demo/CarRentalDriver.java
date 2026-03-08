package org.dynamik.demo;

import org.dynamik.constants.State;
import org.dynamik.constants.VehicleBookingType;
import org.dynamik.constants.VehicleType;
import org.dynamik.exception.ResourceNotFoundException;
import org.dynamik.model.Branch;
import org.dynamik.model.City;
import org.dynamik.model.Slot;
import org.dynamik.model.Vehicle;
import org.dynamik.service.BranchService;
import org.dynamik.service.CarRentalServiceImpl;
import org.dynamik.service.VehicleService;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CarRentalDriver {
    private VehicleService vehicleService;
    private CarRentalServiceImpl carRentalService;
    private BranchService branchService;


    public CarRentalDriver() {
        this.vehicleService = new VehicleService();
        carRentalService = CarRentalServiceImpl.getInstance();
        branchService  = new BranchService();
    }

    public void demo()  {
        City city = initCity();
        List<Vehicle> vehicles = initVehicle();
        Branch branch = initBranch(vehicles, city);

        // onboard branch
        branchService.save(branch);

        // add new vehicle
        LocalDateTime startDateTime = LocalDateTime.of(2026, 3, 8, 10,0);
        LocalDateTime endDateTime = LocalDateTime.of(2026, 3, 8, 11,0);
        Slot slot = new Slot(startDateTime, endDateTime);
        Vehicle vehicle5 = new Vehicle("xuv05", VehicleType.CAR, State.AVAILABLE, 50000l, slot);
        vehicle5.setCreatedAt(System.currentTimeMillis());
        vehicle5.setId("vid5");

        try {
            branchService.addVehicle(branch.getId(), vehicle5);
        } catch (Exception  |ResourceNotFoundException ex) {
           ex.printStackTrace();
        }


        // requirement , display vehicles
       List<Vehicle> vehicleSortedOrder = carRentalService.displayVehicle(branch.getId());
       vehicleSortedOrder.forEach(vehicle -> System.out.println(vehicle.getName() + " price : " + vehicle.getPrice()));

       //book vehicle
        startDateTime = LocalDateTime.of(2026, 3, 3, 10,0);
        endDateTime = LocalDateTime.of(2026, 3, 3, 11,0);
        Slot requestedSlot = new Slot(startDateTime, endDateTime);
        Vehicle vehicleToBook = carRentalService.getVehicleForAGivenSlot(branch.getId(), requestedSlot, VehicleType.CAR, VehicleBookingType.PRICE);
        try {
            carRentalService.bookVehicle(vehicleToBook, branch);
        } catch (Exception | ResourceNotFoundException ex) {
            ex.printStackTrace();
        }

        System.out.println("Vehicle booked : " + vehicleToBook.getName() + " state : " + vehicleToBook.getState());


        // release vehicle
        try {
            carRentalService.releaseVehicle(vehicleToBook);
        } catch (Exception | ResourceNotFoundException ex) {
            ex.printStackTrace();
        }

        System.out.println("Vehicle released : " + vehicleToBook.getName() + " state : " + vehicleToBook.getState());
    }

    private City initCity() {
        City city = new City("delhi", "110093");
        city.setId("cid1");
        city.setCreatedAt(System.currentTimeMillis());
        return city;
    }

    private Branch initBranch(List<Vehicle> vehicles, City city) {
        Branch branch = new Branch("karkardooma", vehicles, city);
        branch.setId("bid1");
        branch.setCreatedAt(System.currentTimeMillis());
        return branch;
    }

    private List<Vehicle> initVehicle() {
        LocalDateTime startDateTime = LocalDateTime.of(2026, 3, 1, 10,0);
        LocalDateTime endDateTime = LocalDateTime.of(2026, 3, 1, 11,0);
        Slot slot = new Slot(startDateTime, endDateTime);
        Vehicle vehicle1 = new Vehicle("xuv01", VehicleType.CAR, State.AVAILABLE, 10000l, slot);

         startDateTime = LocalDateTime.of(2026, 3, 2, 10,0);
         endDateTime = LocalDateTime.of(2026, 3, 2, 11,0);
         slot = new Slot(startDateTime, endDateTime);
        Vehicle vehicle2 = new Vehicle("xuv02", VehicleType.CAR, State.AVAILABLE, 20000l, slot);

         startDateTime = LocalDateTime.of(2026, 3, 3, 10,0);
         endDateTime = LocalDateTime.of(2026, 3, 3, 11,0);
         slot = new Slot(startDateTime, endDateTime);
        Vehicle vehicle3 = new Vehicle("xuv03", VehicleType.CAR, State.AVAILABLE, 30000l, slot);


        startDateTime = LocalDateTime.of(2026, 3, 3, 10,0);
        endDateTime = LocalDateTime.of(2026, 3, 3, 11,0);
         slot = new Slot(startDateTime, endDateTime);
        Vehicle vehicle4 = new Vehicle("xuv04", VehicleType.CAR, State.AVAILABLE, 40000l, slot);

        vehicle1.setId("vid1");
        vehicle2.setId("vid2");
        vehicle3.setId("vid3");
        vehicle4.setId("vid4");

        vehicle1.setCreatedAt(System.currentTimeMillis());
        vehicle2.setCreatedAt(System.currentTimeMillis());
        vehicle3.setCreatedAt(System.currentTimeMillis());
        vehicle4.setCreatedAt(System.currentTimeMillis());

        List<Vehicle> vehicleList = new ArrayList<>(Arrays.asList(vehicle1, vehicle2, vehicle3, vehicle4));
        vehicleService.saveAll(vehicleList);
        return vehicleList;
    }
}
