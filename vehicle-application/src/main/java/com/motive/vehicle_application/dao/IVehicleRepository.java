package com.motive.vehicle_application.dao;

import com.motive.vehicle_application.model.VehicleEntity;
import org.springframework.data.jpa.repository.JpaRepository;


public interface IVehicleRepository extends JpaRepository<VehicleEntity, String> {
}
