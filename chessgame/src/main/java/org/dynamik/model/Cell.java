package org.dynamik.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.dynamik.model.pieces.Piece;

@Data
public class Cell {
    private int row;
    private int col;
    private Piece piece;

    public Cell(int row, int col) {
        this.row = row;
        this.col = col;
    }
}
