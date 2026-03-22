package org.dynamik.model.pieces;

import lombok.Data;
import org.dynamik.model.Board;
import org.dynamik.model.Cell;
import org.dynamik.model.Color;

@Data
public abstract class Piece {
    protected final Color color;
    public abstract Boolean canMove(Board board, Cell from, Cell to);

    public Piece(Color color) {
        this.color = color;
    }
}
