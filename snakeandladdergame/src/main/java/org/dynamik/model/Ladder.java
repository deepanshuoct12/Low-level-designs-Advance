package org.dynamik.model;


public class Ladder extends BoardEntity{
    public Ladder(Long start, Long end) {
        super(start, end);
        if (start>=end) {
            throw new IllegalArgumentException("ladder : start should be less than end : start : " + start + " end : " + end);
        }
    }
}
