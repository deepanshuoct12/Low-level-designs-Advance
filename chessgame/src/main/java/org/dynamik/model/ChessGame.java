package org.dynamik.model;

import lombok.Data;
import org.dynamik.exception.InvalidInputException;
import org.dynamik.model.pieces.Piece;

import java.util.Scanner;

@Data
public class ChessGame {
    private Board board;
    private Player blackPlayer, whitePlayer, currentPlayer;

    public ChessGame() {
        board = new Board();
    }

    public void setPlayers(Player whitePlayer, Player blackPlayer) {
        this.whitePlayer = whitePlayer;
        this.blackPlayer = blackPlayer;
        currentPlayer = whitePlayer;
    }

    public void startGame() {
        while(!isGameOver()) {
            Player player = currentPlayer;
            System.out.println("Player turn : " + player.getName());

            Move move = getPlayerMove(player);
            excerciseMove(move);
            switchTurn();

        }
    }

    private void switchTurn() {
        currentPlayer = (currentPlayer == blackPlayer?whitePlayer:blackPlayer);
    }

    private synchronized void excerciseMove(Move move) {
        Cell source = move.getStart();
        Cell destination = move.getEnd();
        Piece piece = source.getPiece();
        if (piece.canMove(board, source, destination)) {
            source.setPiece(null);
            destination.setPiece(piece);
        }
    }

    private Move getPlayerMove(Player player) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter source row : ");
        int sr = scanner.nextInt();

        System.out.println("Enter source col : ");
        int sc = scanner.nextInt();



        System.out.println("Enter destination row : ");
        int dr = scanner.nextInt();

        System.out.println("Enter destination col : ");
        int dc = scanner.nextInt();

        Piece piece = board.getPiece(sr, sc);
        if (piece == null || piece.getColor() != player.getColor()) {
            throw new IllegalArgumentException("invalid input");
        }

        return new Move(board.getCell(sr, sc), board.getCell(dr, dc));
    }

    private boolean isGameOver() {
        return board.isCheckMate(Color.WHITE) || board.isCheckMate(Color.BLACK) || board.isStaleMate(Color.WHITE) || board.isStaleMate(Color.BLACK);
    }
}
