package org.project.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.project.enums.MatchState;
import org.project.enums.MatchType;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class Match extends BaseEntity {
    private String name;
    private MatchState state;
    private Long team1Id;
    private Long team2Id;
    private Long tossWinnerTeamId;
    private String tossDecision;
    private MatchType matchType;
}
