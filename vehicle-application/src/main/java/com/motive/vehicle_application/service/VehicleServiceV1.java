package com.motive.vehicle_application.service;

import com.motive.vehicle_application.dao.IVehicleRepository;
import com.motive.vehicle_application.dto.NHTSAResponseDTO;
import com.motive.vehicle_application.dto.VehicleDTO;
import com.motive.vehicle_application.dto.VehicleResponseDTO;
import com.motive.vehicle_application.model.VehicleEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static com.motive.vehicle_application.constants.VehicleConstants.*;

@Service
public class VehicleServiceV1 implements IVehicleService{
    @Autowired private RestTemplate restTemplate;
    @Autowired private IVehicleRepository iVehicleRepository;

    @Override
    public Boolean isApplicable(String key) {
        return V1.equals(key);
    }

    // h2 db save
    @Override
    public VehicleResponseDTO getModels(String make, Integer year) {
        String format = "json";
        String url = String.format(
                NHTS_URL, make, year, format
        );
        NHTSAResponseDTO nhtsaResponseDTO = restTemplate.getForObject(url, NHTSAResponseDTO.class);

        //   1. without saving to db
//        List<VehicleDTO> vehicleDTOList = nhtsaResponseDTO.getResults().stream().map(
//                nhtsaResponse -> {
//                    VehicleDTO vehicleDTO = new VehicleDTO();
//                    vehicleDTO.setDiscontinued(nhtsaResponse.getModelId() >= 1000 && nhtsaResponse.getModelId() <= 3000);
//                    vehicleDTO.setModelName(nhtsaResponse.getModelName());
//                    vehicleDTO.setMakeName(nhtsaResponse.getMakeName());
//                    return vehicleDTO;
//                }).collect(Collectors.toList());


        //  2. saving to h2 database
        List<VehicleEntity> vehicleEntities = nhtsaResponseDTO.getResults().stream().map(
                nhtsaResponse -> {
                    VehicleEntity vehicleEntity = new VehicleEntity();
                    vehicleEntity.setDiscontinued(nhtsaResponse.getModelId() >= 1000 && nhtsaResponse.getModelId() <= 3000);
                    vehicleEntity.setModelName(nhtsaResponse.getModelName());
                    vehicleEntity.setMakeName(nhtsaResponse.getMakeName());
                    vehicleEntity.setMakeId(nhtsaResponse.getMakeId().toString());
                    return vehicleEntity;
                }).collect(Collectors.toList());


        iVehicleRepository.saveAll(vehicleEntities);
        VehicleResponseDTO vehicleResponseDTO = new VehicleResponseDTO();
        List<VehicleDTO> vehicleDTOList = new ArrayList<>();
        for (VehicleEntity vehicleEntity : vehicleEntities) {
            VehicleDTO vehicleDTO = new VehicleDTO(vehicleEntity.getModelName(), vehicleEntity.getMakeName(), vehicleEntity.getDiscontinued(), vehicleEntity.getId());
            vehicleDTOList.add(vehicleDTO);
        }

        vehicleResponseDTO.setResults(vehicleDTOList);
        return vehicleResponseDTO;
    }

    @Override
    public VehicleResponseDTO getDiscontinuesModels(String make, Integer year) {
        List<VehicleEntity> vehicleEntities = iVehicleRepository.findAll();
        vehicleEntities = vehicleEntities.stream().filter(VehicleEntity::getDiscontinued).collect(Collectors.toList());
        VehicleResponseDTO vehicleResponseDTO = new VehicleResponseDTO();
        List<VehicleDTO> vehicleDTOList = new ArrayList<>();
        for (VehicleEntity vehicleEntity : vehicleEntities) {
            VehicleDTO vehicleDTO = new VehicleDTO(vehicleEntity.getModelName(), vehicleEntity.getMakeName(), vehicleEntity.getDiscontinued(), vehicleEntity.getId());
            vehicleDTOList.add(vehicleDTO);
        }


        vehicleResponseDTO.setResults(vehicleDTOList);
        return vehicleResponseDTO;


        // without h2 database
//        VehicleResponseDTO vehicleResponseDTO =  getModels(make, year);
//        List<VehicleDTO> vehicleDTOList = vehicleResponseDTO.getResults().stream().filter(VehicleDTO::getDiscontinued).collect(Collectors.toList());
//        vehicleResponseDTO.setResults(vehicleDTOList);
//        return vehicleResponseDTO;
    }
}
