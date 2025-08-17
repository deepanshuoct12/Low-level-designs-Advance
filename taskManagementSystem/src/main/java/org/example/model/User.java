package org.example.model;

import lombok.Data;
import org.example.enums.UserType;

@Data
public class User {
    private String id;
    private String name;
    private Integer age;
    private String emailId;
    private UserType userType;
}
