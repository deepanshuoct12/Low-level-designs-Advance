package org.dynamik.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import org.dynamik.enums.UserRole;

@Data
public class User {
    private String id;
    private String name;
    private String email;
    private UserRole role;
}
