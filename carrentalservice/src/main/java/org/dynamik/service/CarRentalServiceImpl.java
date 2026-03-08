package org.dynamik.service;

import org.dynamik.constants.State;
import org.dynamik.constants.VehicleBookingType;
import org.dynamik.constants.VehicleType;
import org.dynamik.exception.ResourceNotFoundException;
import org.dynamik.model.Booking;
import org.dynamik.model.Branch;
import org.dynamik.model.Slot;
import org.dynamik.model.Vehicle;
import org.dynamik.stratergy.IVehicleBookStratergy;
import org.dynamik.stratergy.PriceStratergy;
import java.util.concurrent.locks.ReentrantLock;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class CarRentalServiceImpl implements ICarRentalService {
    private VehicleService vehicleService;
    private BookingService bookingService;
    private BranchService branchService;
    private static CarRentalServiceImpl carRentalService;
    private List<IVehicleBookStratergy> vehicleBookStratergies;

    public CarRentalServiceImpl() {
        vehicleService = new VehicleService();
        bookingService = new BookingService();
        branchService  = new BranchService();
        vehicleBookStratergies = new ArrayList<>();
        vehicleBookStratergies.add(new PriceStratergy());
    }

    public static CarRentalServiceImpl getInstance() {
        if (carRentalService == null) {
            synchronized (CarRentalServiceImpl.class) {
                if (carRentalService == null) {
                    carRentalService = new CarRentalServiceImpl();
                }
            }
        }

        return carRentalService;
    }

    @Override
    public List<Vehicle> displayVehicle(String branchId) {
        List<Vehicle> vehicles = branchService.findById(branchId).getVehicles();
        vehicles.sort(new Comparator<Vehicle>() {
            @Override
            public int compare(Vehicle o1, Vehicle o2) {
                return Long.compare(o1.getPrice(),o2.getPrice());
            }
        });

        return vehicles;
    }

    @Override
    public Vehicle getVehicleForAGivenSlot(String branchId, Slot slot, VehicleType vehicleType, VehicleBookingType vehicleBookingType) {
        return vehicleBookStratergies.stream().filter(iVehicleBookStratergy -> iVehicleBookStratergy.isApplicable(vehicleBookingType)).findFirst().get().getVehicle(branchId, vehicleType, slot);
    }

    @Override
    public void bookVehicle(Vehicle vehicle, Branch branch) throws ResourceNotFoundException {
        ReentrantLock lock = vehicleService.getVehicleLock(vehicle.getId());
        lock.lock();
        try {
            Vehicle existingVehicle = vehicleService.findById(vehicle.getId());
            if (existingVehicle == null) {
                throw new ResourceNotFoundException("Vehicle not found");
            }
            
            if (existingVehicle.getState() == State.UNAVAILABLE) {
                throw new IllegalStateException("Vehicle is already booked");
            }

            existingVehicle.setState(State.UNAVAILABLE);
            branch.getVehicles().stream().filter(vehicleObj -> vehicleObj.getId().equals(existingVehicle.getId())).findFirst().ifPresent(v -> v.setState(State.UNAVAILABLE));
            
            Booking booking = new Booking(existingVehicle.getSlot(), existingVehicle, branch);
            bookingService.save(booking);
            vehicleService.update(existingVehicle);
        } finally {
            lock.unlock();
        }
    }

    @Override
    public void releaseVehicle(Vehicle vehicle) throws ResourceNotFoundException{
        ReentrantLock lock = vehicleService.getVehicleLock(vehicle.getId());
        lock.lock();
        try {
            Vehicle existingVehicle = vehicleService.findById(vehicle.getId());
            if (existingVehicle == null) {
                throw new ResourceNotFoundException("Vehicle not found");
            }

            if (existingVehicle.getState() == State.AVAILABLE) {
                throw new IllegalStateException("Vehicle is already available");
            }

            existingVehicle.setState(State.AVAILABLE);
            vehicleService.update(existingVehicle);
        } finally {
            lock.unlock();
        }
    }
}
