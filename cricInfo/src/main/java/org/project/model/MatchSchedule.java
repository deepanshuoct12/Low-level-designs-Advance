package org.project.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class MatchSchedule extends BaseEntity {
    private Long matchId;
    private LocalDateTime startDateTime;
    private LocalDateTime endDateTime;
    private String venue;
    private String city;
    private String country;
    private Integer overs;
}
