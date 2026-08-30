package org.project.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.project.enums.BuisnessType;

@Data
public class Merchant extends BaseEntity {
    private String name;
    private BuisnessType buisnessType;
}
