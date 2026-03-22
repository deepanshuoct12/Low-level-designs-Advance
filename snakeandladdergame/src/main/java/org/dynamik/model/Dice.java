package org.dynamik.model;

public class Dice {
    private Long minVal;
    private Long maxVal;

    public Dice(Long minVal, Long maxVal) {
        this.minVal = minVal;
        this.maxVal = maxVal;
    }

    public Long rollDice() {
        return (long) (Math.random() * (maxVal - minVal) + minVal);
    }
}
