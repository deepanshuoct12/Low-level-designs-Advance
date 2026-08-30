package org.project.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class User extends BaseEntity {
    private Long id;
    private String name;
    private String email;
    private String phoneNumber;
}
