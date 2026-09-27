package org.project.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class User extends BaseEntity {
    private String name;
    private Integer age;
    private String emailId;
}
