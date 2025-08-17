package org.dynamik.model;

import lombok.Data;
import org.dynamik.enums.UserType;

@Data
public class User extends AbstractEntity{
    private String name;
    private String email;
    private String picture_url;
    private String headline;
    private String summary;
    private String description;
    private String education;
    private String skills;
    private UserType type;
    private Long experience;
    private String companyId;
}
