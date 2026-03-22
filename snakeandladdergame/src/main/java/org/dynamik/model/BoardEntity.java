package org.dynamik.model;

import lombok.Data;

@Data
public abstract class BoardEntity {
    private Long start;
    private Long end;

    public BoardEntity(Long start, Long end) {
        this.start = start;
        this.end = end;
    }
}
