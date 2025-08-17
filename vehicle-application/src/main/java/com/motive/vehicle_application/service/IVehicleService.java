package com.motive.vehicle_application.service;

import com.motive.vehicle_application.dto.VehicleResponseDTO;

public interface IVehicleService {
     Boolean isApplicable(String key);
     VehicleResponseDTO getModels(String make,Integer year);
     VehicleResponseDTO getDiscontinuesModels(String make,Integer year);
}
