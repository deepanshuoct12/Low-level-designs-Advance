package org.dynamik.service;

import org.dynamik.dao.ParkingSpotDao;
import org.dynamik.model.ParkingSpot;
import org.dynamik.constants.BookingStatus;
import org.dynamik.constants.VehicleType;

import java.util.List;

public class ParkingSpotService {
    private final ParkingSpotDao parkingSpotDao;

    public ParkingSpotService() {
        this.parkingSpotDao = new ParkingSpotDao();
    }

    public ParkingSpot save(ParkingSpot parkingSpot) {
        return parkingSpotDao.save(parkingSpot);
    }

    public ParkingSpot findById(String id) {
        return parkingSpotDao.findById(id);
    }

    public void deleteById(String id) {
        parkingSpotDao.deleteById(id);
    }

    public void update(ParkingSpot parkingSpot) {
        parkingSpotDao.update(parkingSpot);
    }

    public List<ParkingSpot> getAll() {
        return parkingSpotDao.getAll();
    }

    public ParkingSpot findByNumber(String number) {
        return parkingSpotDao.findByNumber(number);
    }

    public List<ParkingSpot> findByLevelId(String levelId) {
        return parkingSpotDao.findByLevelId(levelId);
    }

    public List<ParkingSpot> findByBookingStatus(BookingStatus bookingStatus) {
        return parkingSpotDao.findByBookingStatus(bookingStatus);
    }

    public List<ParkingSpot> findAvailableSpotsByLevel(String levelId) {
        return parkingSpotDao.findAvailableSpotsByLevel(levelId);
    }

    public List<ParkingSpot> findByVehicleType(VehicleType vehicleType) {
        return parkingSpotDao.findByVehicleType(vehicleType);
    }

    public List<ParkingSpot> findAvailableSpotsByVehicleType(VehicleType vehicleType) {
        return parkingSpotDao.findAvailableSpotsByVehicleType(vehicleType);
    }

//    public ParkingSpot findByVehicleId(String vehicleId) {
//        return parkingSpotDao.getAll().stream().filter(parkingSpot -> parkingSpot.getVehicleId().equals(vehicleId)).findFirst().orElse(null);
//    }
}
