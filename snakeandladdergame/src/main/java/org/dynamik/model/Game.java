package org.dynamik.model;

import org.dynamik.constants.GameStatus;

import java.util.Deque;

import static org.dynamik.constants.GameStatus.FINISHED;
import static org.dynamik.constants.GameStatus.RUNNING;

public class Game {
    private final Board board;
    private final Deque<Player> players;
    private  GameStatus gameStatus;
    private final Dice dice;


    public Game(Board board, Deque<Player> players, GameStatus gameStatus, Dice dice) {
        this.board = board;
        this.players = players;
        this.gameStatus = gameStatus;
        this.dice = dice;
    }

    public void startGame() {
        gameStatus = RUNNING;
        System.out.println("GAME STARTED ");

        while(RUNNING.equals(gameStatus)) {
            Player player = players.poll();
            takeTurn(player);

            if (gameStatus.equals(RUNNING)) {
                players.push(player);
            }
        }
    }

    private void takeTurn(Player player) {
       Long diceVal = dice.rollDice();
       Long nextPosition = player.getCurrentPosition() + diceVal;

       Long finalPosition = board.getFinalPosition(nextPosition);

       if (finalPosition>nextPosition) {
           System.out.println("PLAYER : " + player.getName() + " took the ladder to : " + finalPosition);
       } else if (finalPosition<nextPosition) {
           System.out.println("PLAYER : " + player.getName() + " cut by snake and moving to : " + finalPosition);
       } else {
           System.out.println("PLAYER : " + player.getName() +  " reached final position to : "  + finalPosition);
       }

       if (finalPosition==board.getSize()) {
           System.out.println("WINNER IS : " + player.getName());
           gameStatus = FINISHED;
       }

       if (diceVal==6) {
           takeTurn(player);
       }
    }
}
