package com.motive.vehicle_application.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResultDTO {
    @JsonProperty("Make_ID")
    private Integer makeId;

    @JsonProperty("Make_Name")
    private String makeName;

    @JsonProperty("Model_ID")
    private Integer modelId;

    @JsonProperty("Model_Name")
    private String modelName;

    @JsonProperty("VehicleTypeId")
    private String vehicleTypeId;

    @JsonProperty("VehicleTypeName")
    private String vehicleTypeName;
}
