package com.motive.vehicle_application.service;

import com.motive.vehicle_application.dto.NHTSAResponseDTO;
import com.motive.vehicle_application.dto.VehicleDTO;
import com.motive.vehicle_application.dto.VehicleResponseDTO;
import com.motive.vehicle_application.model.VehicleMongoEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.stream.Collectors;

import static com.motive.vehicle_application.constants.VehicleConstants.NHTS_URL;
import static com.motive.vehicle_application.constants.VehicleConstants.V3;

@Service
public class VehicleServiceV3 implements IVehicleService {
    @Autowired
    private MongoTemplate mongoTemplate;

    @Autowired
    private RestTemplate restTemplate;

    @Override
    public Boolean isApplicable(String key) {
        return V3.equals(key);
    }

    @Override
    public VehicleResponseDTO getModels(String make, Integer year) {
        String format = "json";
        String url = String.format(
                NHTS_URL, make, year, format
        );

        NHTSAResponseDTO response = restTemplate.getForObject(url, NHTSAResponseDTO.class);
        List<VehicleMongoEntity> vehicles = response.getResults().stream()
                .map(r -> {
                    VehicleMongoEntity entity = new VehicleMongoEntity();
                    entity.setDiscontinued(r.getModelId() >= 1000 && r.getModelId() <= 3000);
                    entity.setModelName(r.getModelName());
                    entity.setMakeName(r.getMakeName());
                    entity.setMakeId(r.getMakeId().toString());
                    return entity;
                }).collect(Collectors.toList());

        // Save all using MongoTemplate
        mongoTemplate.insertAll(vehicles);

        List<VehicleDTO> dtoList = vehicles.stream()
                .map(e -> new VehicleDTO(e.getModelName(), e.getMakeName(), e.getDiscontinued(), e.getId()))
                .collect(Collectors.toList());

        VehicleResponseDTO result = new VehicleResponseDTO();
        result.setResults(dtoList);
        return result;
    }

    @Override
    public VehicleResponseDTO getDiscontinuesModels(String make, Integer year) {
        // Build query to find only discontinued vehicles
        Query query = new Query(Criteria.where("discontinued").is(true));
        List<VehicleMongoEntity> vehicles = mongoTemplate.find(query, VehicleMongoEntity.class);

        List<VehicleDTO> dtoList = vehicles.stream()
                .map(e -> new VehicleDTO(e.getModelName(), e.getMakeName(), e.getDiscontinued(), e.getId()))
                .collect(Collectors.toList());

        VehicleResponseDTO result = new VehicleResponseDTO();
        result.setResults(dtoList);
        return result;
    }
}
