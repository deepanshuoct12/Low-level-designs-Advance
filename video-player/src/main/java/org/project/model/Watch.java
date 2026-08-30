package org.project.model;

import org.project.enums.WatchState;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class Watch extends BaseEntity {
    private Long userId;
    private Long contentId;
    private Long watchedDuration; // in seconds
    private Long lastWatchedPosition; // timestamp/seconds
    private WatchState state;
}
