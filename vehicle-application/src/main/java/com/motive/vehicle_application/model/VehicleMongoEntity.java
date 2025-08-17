package com.motive.vehicle_application.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(value = "Vehicle")
public class VehicleMongoEntity {
    @Id
    private String id;
    private String modelName;
    private String makeName;
    private Boolean discontinued;
    private String makeId;
}
