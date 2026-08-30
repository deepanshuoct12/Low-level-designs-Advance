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
public class CurrentScore extends BaseEntity {
    private Long matchId;
    private Long battingTeamId;
    private Long bowlingTeamId;
    private Integer totalRuns;
    private Integer totalWickets;
    private Double overs;
    private Integer currentOverBalls;
    private Long strikerBatsmanId;
    private Long nonStrikerBatsmanId;
    private Long currentBowlerId;
    private Integer currentOverRuns;
    private Integer requiredRuns;
    private Double requiredRunRate;
    private Double currentRunRate;
    private Integer targetScore;
}
