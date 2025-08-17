package com.motive.vehicle_application.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class VehicleDTO {
    private String modelName;
    private String makeName;
    private Boolean discontinued;
    private String  id;
}
