package org.project.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class TeamStanding extends BaseEntity {
    private Long teamId;
    private String teamName;
    private Integer rank;
    private Integer matchesPlayed;
    private Integer matchesWon;
    private Integer matchesLost;
    private Integer points;
}
