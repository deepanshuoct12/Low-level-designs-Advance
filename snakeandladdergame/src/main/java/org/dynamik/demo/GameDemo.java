package org.dynamik.demo;

import org.dynamik.constants.GameStatus;
import org.dynamik.model.*;

import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Deque;
import java.util.List;

public class GameDemo {
    public void demo() {
        // Create board with snakes and ladders
        Board board = createBoard();
        
        // Create players
        Deque<Player> players = createPlayers();
        
        // Create dice
        Dice dice = new Dice(1L, 6L);
        
        // Create and start game
        Game game = new Game(board, players, GameStatus.RUNNING, dice);
        game.startGame();
    }
    
    private Board createBoard() {
        // Add some snakes and ladders
        List<BoardEntity> entities = Arrays.asList(
            new Ladder(3L, 22L),
            new Ladder(5L, 8L),
            new Ladder(11L, 26L),
            new Ladder(20L, 29L),
            new Ladder(27L, 84L),
            new Ladder(21L, 82L),
            new Ladder(17L, 74L),
            new Snake(30L, 7L),
            new Snake(54L, 34L),
            new Snake(88L, 24L),
            new Snake(62L, 18L),
            new Snake(99L, 78L),
            new Snake(95L, 56L),
            new Snake(93L, 73L),
            new Snake(64L, 60L)
        );
        
        return new Board(100L, entities);
    }
    
    private Deque<Player> createPlayers() {
        Player player1 = new Player();
        player1.setName("Alice");
        player1.setCurrentPosition(0L);
        
        Player player2 = new Player();
        player2.setName("Bob");
        player2.setCurrentPosition(0L);
        
        Deque<Player> players = new ArrayDeque<>();
        players.add(player1);
        players.add(player2);
        
        return players;
    }
}
