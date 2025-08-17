package com.motive.vehicle_application.dto;


import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class NHTSAResponseDTO {
    @JsonProperty(value = "Count")
    private Integer count;
    @JsonProperty(value = "Message")
    private String message;
    @JsonProperty(value = "SearchCriteria")
    private String searchCriteria;
    @JsonProperty(value = "Results")
    private List<ResultDTO> results;
}
