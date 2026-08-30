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
public class Team extends BaseEntity {
    private String name;
    private String description;
    private String country;
    private String captain;
    private Integer ranking;
    private Integer totalMatchesPlayed;
    private Integer totalWins;
    private Integer totalLosses;
}
