package org.project.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class Leaderboard extends BaseEntity {
    private Long tournamentId;
    private String tournamentName;
    private List<TeamStanding> teamStandings;
}
