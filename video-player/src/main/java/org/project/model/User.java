package org.project.model;

import org.project.enums.Genre;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class User extends BaseEntity {
    private String username;
    private String email;
    private Genre preferences;
}
