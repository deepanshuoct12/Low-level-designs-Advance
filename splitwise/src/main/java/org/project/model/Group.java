package org.project.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
public class Group extends BaseEntity {
    private String name;
    private List<String> memberIds;
    private String createdBy;
}
