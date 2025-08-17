package org.dynamik.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Job extends AbstractEntity {
    private String title;
    private String description;
    private String location;
    private String companyId;
    private String recruiterId; // userId
    private String applyUrl;
}
