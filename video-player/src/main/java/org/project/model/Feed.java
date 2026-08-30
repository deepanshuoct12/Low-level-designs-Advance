package org.project.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class Feed extends BaseEntity {
    private Long userId;
    private Long contentId;
}
