package org.project.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class BowlerStats extends Statistics {
    private Integer totalWicketsTaken;
    private Integer totalRunsConceded;
    private Integer totalOversBowled;
    private Double economyRate;
    private Integer totalMaidens;
}
