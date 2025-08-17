package com.motive.vehicle_application.controller;


import com.motive.vehicle_application.dto.VehicleResponseDTO;
import com.motive.vehicle_application.service.IVehicleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static com.motive.vehicle_application.constants.VehicleConstants.*;

@RestController
@RequestMapping("/vehicles")
public class VehicleController {

    private final List<IVehicleService> IVehicleServices;

    @Autowired
    public VehicleController(List<IVehicleService> IVehicleServices) {
        this.IVehicleServices = IVehicleServices;
    }

    /////////////// v1 apis //////////////////
    @GetMapping("/v1/{make}/{year}")
    ResponseEntity<VehicleResponseDTO> getAllModels(@PathVariable String make,@PathVariable Integer year) {
        return new ResponseEntity<>(IVehicleServices.stream().filter(iVehicleService -> iVehicleService.isApplicable(V1)).findAny().get().getModels(make, year),HttpStatus.OK);
    }


    @GetMapping("/v1/{make}/{year}/discontinues")
    ResponseEntity<VehicleResponseDTO> getDiscontinuesModels(@PathVariable String make,@PathVariable Integer year) {
        return new ResponseEntity<>(IVehicleServices.stream().filter(iVehicleService -> iVehicleService.isApplicable(V1)).findAny().get().getDiscontinuesModels(make, year),HttpStatus.OK);
    }


    /////////////// v2 apis //////////////////
    @GetMapping("/v2/{make}/{year}")
    ResponseEntity<VehicleResponseDTO> getAllModelsV2(@PathVariable String make,@PathVariable Integer year) {
        return new ResponseEntity<>(IVehicleServices.stream().filter(iVehicleService -> iVehicleService.isApplicable(V2)).findAny().get().getModels(make, year),HttpStatus.OK);
    }


    @GetMapping("/v2/{make}/{year}/discontinues")
    ResponseEntity<VehicleResponseDTO> getDiscontinuesModelsV2(@PathVariable String make,@PathVariable Integer year) {
        return new ResponseEntity<>(IVehicleServices.stream().filter(iVehicleService -> iVehicleService.isApplicable(V2)).findAny().get().getDiscontinuesModels(make, year),HttpStatus.OK);
    }


    /////////////// v3 apis //////////////////
    @GetMapping("/v3/{make}/{year}")
    ResponseEntity<VehicleResponseDTO> getAllModelsV3(@PathVariable String make,@PathVariable Integer year) {
        return new ResponseEntity<>(IVehicleServices.stream().filter(iVehicleService -> iVehicleService.isApplicable(V3)).findAny().get().getModels(make, year),HttpStatus.OK);
    }


    @GetMapping("/v3/{make}/{year}/discontinues")
    ResponseEntity<VehicleResponseDTO> getDiscontinuesModelsV3(@PathVariable String make,@PathVariable Integer year) {
        return new ResponseEntity<>(IVehicleServices.stream().filter(iVehicleService -> iVehicleService.isApplicable(V3)).findAny().get().getDiscontinuesModels(make, year),HttpStatus.OK);
    }
}
