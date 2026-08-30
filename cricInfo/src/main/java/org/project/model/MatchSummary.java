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
public class MatchSummary extends BaseEntity {
    private Long matchId;
    private Long winnerTeamId;
    private Integer margin;
    private Long manOfTheMatchId;
    private Long highestRunScorerId;
    private Integer highestRuns;
    private Long highestWicketTakerId;
    private Integer highestWickets;
    private String matchResult;
    private Double totalMatchDuration;
    private String tossWinnerTeam;
}
