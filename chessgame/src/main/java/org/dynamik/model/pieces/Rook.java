package org.dynamik.model.pieces;

import org.dynamik.model.Board;
import org.dynamik.model.Cell;
import org.dynamik.model.Color;

public class Rook extends Piece{
    public Rook(Color color) {
        super(color);
    }

    @Override
    public Boolean canMove(Board board, Cell from, Cell to) {
        int sr = from.getRow();
        int sc = from.getCol();

        int dr = to.getRow();
        int dc = to.getCol();

        int rowdiff = Math.abs(sr-sc);
        int coldiff = Math.abs(dr-dc);


        return (sr==dr || sc==dc);
    }
}
