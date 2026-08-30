package org.project.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class BatsmanStats extends Statistics {
    private Integer totalRunsScored;
    private Integer totalBallsFaced;
    private Double strikeRate;
    private Double battingAverage;
    private Integer highestScore;
}
