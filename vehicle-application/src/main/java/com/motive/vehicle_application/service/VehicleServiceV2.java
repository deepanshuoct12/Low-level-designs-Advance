package com.motive.vehicle_application.service;

import com.motive.vehicle_application.dao.IVehicleMongoCrudRepository;
import com.motive.vehicle_application.dto.NHTSAResponseDTO;
import com.motive.vehicle_application.dto.VehicleDTO;
import com.motive.vehicle_application.dto.VehicleResponseDTO;
import com.motive.vehicle_application.model.VehicleMongoEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static com.motive.vehicle_application.constants.VehicleConstants.NHTS_URL;
import static com.motive.vehicle_application.constants.VehicleConstants.V2;

@Service
public class VehicleServiceV2 implements IVehicleService {
    @Autowired private IVehicleMongoCrudRepository iVehicleMongoCrudRepository;
    @Autowired private RestTemplate                restTemplate;


    @Override
    public Boolean isApplicable(String key) {
        return V2.equals(key);
    }

    @Override
    public VehicleResponseDTO getModels(String make, Integer year) {
        String format = "json";
        String url = String.format(
                NHTS_URL, make, year, format
        );
        NHTSAResponseDTO nhtsaResponseDTO = restTemplate.getForObject(url, NHTSAResponseDTO.class);

        List<VehicleMongoEntity> vehicleEntities = nhtsaResponseDTO.getResults().stream().map(
                nhtsaResponse -> {
                    VehicleMongoEntity vehicleEntity = new VehicleMongoEntity();
                    vehicleEntity.setDiscontinued(nhtsaResponse.getModelId() >= 1000 && nhtsaResponse.getModelId() <= 3000);
                    vehicleEntity.setModelName(nhtsaResponse.getModelName());
                    vehicleEntity.setMakeName(nhtsaResponse.getMakeName());
                    vehicleEntity.setMakeId(nhtsaResponse.getMakeId().toString());
                    return vehicleEntity;
                }).collect(Collectors.toList());


        iVehicleMongoCrudRepository.saveAll(vehicleEntities);
        VehicleResponseDTO vehicleResponseDTO = new VehicleResponseDTO();
        List<VehicleDTO> vehicleDTOList = new ArrayList<>();
        for (VehicleMongoEntity vehicleEntity : vehicleEntities) {
            VehicleDTO vehicleDTO = new VehicleDTO(vehicleEntity.getModelName(), vehicleEntity.getMakeName(), vehicleEntity.getDiscontinued(), vehicleEntity.getId());
            vehicleDTOList.add(vehicleDTO);
        }

        vehicleResponseDTO.setResults(vehicleDTOList);
        return vehicleResponseDTO;
    }

    @Override
    public VehicleResponseDTO getDiscontinuesModels(String make, Integer year) {
        List<VehicleMongoEntity> vehicleEntities = iVehicleMongoCrudRepository.findAll();
        vehicleEntities = vehicleEntities.stream().filter(VehicleMongoEntity::getDiscontinued).collect(Collectors.toList());
        VehicleResponseDTO vehicleResponseDTO = new VehicleResponseDTO();
        List<VehicleDTO> vehicleDTOList = new ArrayList<>();
        for (VehicleMongoEntity vehicleEntity : vehicleEntities) {
            VehicleDTO vehicleDTO = new VehicleDTO(vehicleEntity.getModelName(), vehicleEntity.getMakeName(), vehicleEntity.getDiscontinued(), vehicleEntity.getId());
            vehicleDTOList.add(vehicleDTO);
        }


        vehicleResponseDTO.setResults(vehicleDTOList);
        return vehicleResponseDTO;
    }
}
