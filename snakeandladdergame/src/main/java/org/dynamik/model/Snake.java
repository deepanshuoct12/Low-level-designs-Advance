package org.dynamik.model;

public class Snake extends BoardEntity{
    public Snake(Long start, Long end) {
        super(start, end);

        if (start<=end) {
            throw new IllegalArgumentException("snake : start should be greater than end : start : " + start + " end : " + end);
        }
    }
}
