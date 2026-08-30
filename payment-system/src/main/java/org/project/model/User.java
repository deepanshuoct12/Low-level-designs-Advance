package org.project.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
public class User extends BaseEntity {
    private String name;
    private String email;
    private String phone;
}
