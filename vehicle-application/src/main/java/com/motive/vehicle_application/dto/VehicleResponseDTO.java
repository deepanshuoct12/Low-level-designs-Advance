package com.motive.vehicle_application.dto;


import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.util.List;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class VehicleResponseDTO {
    private List<VehicleDTO> results;
}
