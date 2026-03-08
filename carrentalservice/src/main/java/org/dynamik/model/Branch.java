package org.dynamik.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class Branch extends BaseEntity {
    private String name;
    private List<Vehicle> vehicles;
    private City city;
}
