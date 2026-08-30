package org.project.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class User extends BaseEntity {
    private String id;
    private String name;
    private String email;
}
