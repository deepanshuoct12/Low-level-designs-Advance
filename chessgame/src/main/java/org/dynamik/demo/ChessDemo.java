package org.dynamik.demo;

import org.dynamik.model.ChessGame;
import org.dynamik.model.Color;
import org.dynamik.model.Player;

public class ChessDemo {
    public void runDemo() {
        System.out.println("Welcome to Chess Game!");
        
        Player whitePlayer = new Player("White Player", Color.WHITE);
        Player blackPlayer = new Player("Black Player", Color.BLACK);
        
        ChessGame game = new ChessGame();
        game.setPlayers(whitePlayer, blackPlayer);
        
        System.out.println("Starting the game...");
        System.out.println("White pieces move first.");
        System.out.println("Enter coordinates as row and column (0-7)");
        System.out.println("----------------------------------------");
        
        try {
            game.startGame();
            System.out.println("Game Over! Thanks for playing!");
        } catch (Exception e) {
            System.out.println("Game ended with error: " + e.getMessage());
        }
    }
}
