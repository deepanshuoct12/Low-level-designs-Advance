package org.dynamik.model;

import org.dynamik.model.pieces.*;

public class Board {
    private final Cell[][] board;


    public Board() {
        this.board = new Cell[8][8];
        for(int i=0;i<8;i++) {
            for(int j=0;j<8;j++) {
                board[i][j] = new Cell(i,j);
            }
        }

        setUpPieces();
    }

    private void setUpPieces() {
        for(int j=0;j<8;j++) {
            board[1][j].setPiece(new Pawn(Color.WHITE));
            board[6][j].setPiece(new Pawn(Color.BLACK));
        }

        board[0][0].setPiece(new Rook(Color.WHITE));
        board[0][1].setPiece(new Knight(Color.WHITE));
        board[0][2].setPiece(new Bishop(Color.WHITE));
        board[0][3].setPiece(new Queen(Color.WHITE));
        board[0][4].setPiece(new King(Color.WHITE));
        board[0][5].setPiece(new Bishop(Color.WHITE));
        board[0][6].setPiece(new Knight(Color.WHITE));
        board[0][7].setPiece(new Rook(Color.WHITE));

        board[7][0].setPiece(new Rook(Color.BLACK));
        board[7][1].setPiece(new Knight(Color.BLACK));
        board[7][2].setPiece(new Bishop(Color.BLACK));
        board[7][3].setPiece(new Queen(Color.BLACK));
        board[7][4].setPiece(new King(Color.BLACK));
        board[7][5].setPiece(new Bishop(Color.BLACK));
        board[7][6].setPiece(new Knight(Color.BLACK));
        board[7][7].setPiece(new Rook(Color.BLACK));
    }

    public Cell getCell(int r,int c) {
        return board[r][c];
    }


    public boolean isCheckMate(Color color) {
        return false;
    }

    public boolean isStaleMate(Color color) {
       return false;
    }

    public Piece getPiece(int sr, int sc) {
        return board[sr][sc].getPiece();
    }
}
