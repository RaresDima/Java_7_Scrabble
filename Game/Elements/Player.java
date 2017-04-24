package Game.Elements;

import java.util.Vector;
import Game.Game;

public class Player extends Thread {

    private int playerId;
    private Game game;

    public Vector<Character> letters;

    private boolean gameOver = false;

    public Player(int id, Game game) {
        this.playerId = id;
        this.game = game;
        this.letters = new Vector<>();
    }



    @Override
    public void run() {
        while (!gameOver) {
            game.takeTurn(this);

            try {
                sleep(150);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }



    public int getPlayerId() {
        return playerId;
    }
    public void setGameOver(boolean gameOver) {
        this.gameOver = gameOver;
    }
}
