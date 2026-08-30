package org.project.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class BowlingScorecard extends Scorecard {
    private Integer oversBowled;
    private Integer ballsBowled;
    private Integer runsConceded;
    private Integer wicketsTaken;
    private Integer maidens;
    private Double economyRate;
    private Double bowlingAverage;
    private Integer noBalls;
    private Integer wides;
    private Integer dotBalls;
}
