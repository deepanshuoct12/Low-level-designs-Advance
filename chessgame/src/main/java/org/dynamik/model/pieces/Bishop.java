package org.dynamik.model.pieces;

import lombok.Data;
import org.dynamik.model.Board;
import org.dynamik.model.Cell;
import org.dynamik.model.Color;


public class Bishop extends Piece{

    public Bishop(Color color) {
        super(color);
    }

    @Override
    public Boolean canMove(Board board, Cell from, Cell to) {
        int sr = from.getRow();
        int sc = from.getCol();

        int dr = to.getRow();
        int dc = to.getCol();

        return Math.abs(dr-sr) == Math.abs(dc-sc);
    }
}
