package org.project.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.project.enums.ContentType;
import org.project.enums.WatchStatus;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class WatchProgress extends BaseEntity {
    private Long id;
    private Long contentId;
    private Long userId;
    private Long lastTimestamp;
    private Long positionSeconds;
    private WatchStatus status;
    private Long startTimestamp;
    private Long stopTimestamp;
}
