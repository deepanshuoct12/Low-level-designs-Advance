package org.project.model;

import lombok.Data;
import org.project.enums.FlagReason;
import org.project.enums.FlagStatus;
import org.project.enums.TargetType;

@Data
public class Flag extends BaseEntity {
    private String reportedBy;
    private TargetType targetType;
    private String targetId;
    private FlagReason reason;
    private FlagStatus status;
    private String resolvedBy;
}
