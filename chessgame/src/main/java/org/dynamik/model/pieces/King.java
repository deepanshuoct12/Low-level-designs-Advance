package org.dynamik.model.pieces;

import org.dynamik.model.Board;
import org.dynamik.model.Cell;
import org.dynamik.model.Color;

public class King extends Piece{

    public King(Color color) {
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


        return ((rowdiff==1 && coldiff==0) || (rowdiff==0 && coldiff==1));
    }
}
