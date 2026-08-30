package org.project.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class BattingScorecard extends Scorecard {
    private Integer battingOrder;
    private Integer runsScored;
    private Integer ballsFaced;
    private Integer minutes;
    private Double strikeRate;
    private String dismissalType;
    private Long bowlerId;
    private String fielderName;
    private Boolean isNotOut;
    private Integer boundaries;
}
