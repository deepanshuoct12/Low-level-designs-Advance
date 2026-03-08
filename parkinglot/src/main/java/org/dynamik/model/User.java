package org.dynamik.model;

import lombok.Data;

@Data
public class User extends AbstractEntity{
    private String name;
    private String email;
}
