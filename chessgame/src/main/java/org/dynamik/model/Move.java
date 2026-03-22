package org.dynamik.model;

import lombok.Data;

@Data
public class Move {
    private Cell start;
    private Cell end;

    public Move(Cell start, Cell end) {
        this.start = start;
        this.end = end;
    }
}
