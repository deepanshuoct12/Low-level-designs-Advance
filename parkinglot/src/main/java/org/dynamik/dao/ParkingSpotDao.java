package org.dynamik.dao;

import org.dynamik.model.ParkingSpot;
import org.dynamik.constants.BookingStatus;
import org.dynamik.constants.VehicleType;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ParkingSpotDao implements IBaseDao<ParkingSpot, String> {
    private Map<String, ParkingSpot> parkingSpots = new HashMap<>();

    @Override
    public ParkingSpot save(ParkingSpot parkingSpot) {
        return parkingSpots.put(parkingSpot.getId(), parkingSpot);
    }

    @Override
    public ParkingSpot findById(String id) {
        return parkingSpots.get(id);
    }

    @Override
    public void deleteById(String id) {
        parkingSpots.remove(id);
    }

    @Override
    public void update(ParkingSpot parkingSpot) {
        if (parkingSpots.get(parkingSpot.getId()) != null) {
            parkingSpots.put(parkingSpot.getId(), parkingSpot);
        }
    }

    @Override
    public List<ParkingSpot> getAll() {
        return parkingSpots.values().stream().collect(Collectors.toList());
    }

    public ParkingSpot findByNumber(String number) {
        return parkingSpots.values().stream()
                .filter(spot -> number.equals(spot.getNumber()))
                .findFirst()
                .orElse(null);
    }

    public List<ParkingSpot> findByLevelId(String levelId) {
        return parkingSpots.values().stream()
                .filter(spot -> levelId.equals(spot.getLevelId()))
                .collect(Collectors.toList());
    }

    public List<ParkingSpot> findByBookingStatus(BookingStatus bookingStatus) {
        return parkingSpots.values().stream()
                .filter(spot -> bookingStatus.equals(spot.getBookingStatus()))
                .collect(Collectors.toList());
    }

    public List<ParkingSpot> findAvailableSpotsByLevel(String levelId) {
        return parkingSpots.values().stream()
                .filter(spot -> levelId.equals(spot.getLevelId()) && BookingStatus.AVAILABLE.equals(spot.getBookingStatus()))
                .collect(Collectors.toList());
    }

    public List<ParkingSpot> findByVehicleType(VehicleType vehicleType) {
        return parkingSpots.values().stream()
                .filter(spot -> vehicleType.equals(spot.getVehicleType()))
                .collect(Collectors.toList());
    }

    public List<ParkingSpot> findAvailableSpotsByVehicleType(VehicleType vehicleType) {
        return parkingSpots.values().stream()
                .filter(spot -> vehicleType.equals(spot.getVehicleType()) && BookingStatus.AVAILABLE.equals(spot.getBookingStatus()))
                .collect(Collectors.toList());
    }
}
