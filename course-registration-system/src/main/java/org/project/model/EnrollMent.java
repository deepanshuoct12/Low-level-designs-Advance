package org.project.model;

import lombok.Data;


import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Data
public class EnrollMent extends BaseEntity {
    private String courseId;
    private Set<String> users;

    public EnrollMent() {
        setId(String.valueOf(UUID.randomUUID()));
    }

    public Set<String> getUsers() {
        if (users == null) {
            users = new HashSet<>();
        }

        return users;
    }
}
