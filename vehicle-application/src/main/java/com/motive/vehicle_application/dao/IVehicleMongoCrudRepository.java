package com.motive.vehicle_application.dao;

import com.motive.vehicle_application.model.VehicleMongoEntity;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface IVehicleMongoCrudRepository extends MongoRepository<VehicleMongoEntity,String> {

}
