package org.dynamik.model;


import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class City extends BaseEntity {
    private String name;
    private String pinCode;
}
