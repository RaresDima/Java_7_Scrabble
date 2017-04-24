package Game;

import Game.Elements.Bag;
import Game.Elements.Board;
import Game.Elements.Dictionary;
import Game.Elements.Player;

import java.util.Scanner;
import java.util.Vector;

public class Game {

    private Bag bag;
    private Board board;
    private Dictionary dictionary;

    private Player players[];
    private int currentPlayer;

    private int score[];



    public Game(int players) {
        this.bag = new Bag();
        this.board = new Board();
        this.dictionary = new Dictionary("D:\\JetBrains\\projects\\IntelliJProjects\\Java_7\\src\\Game\\Elements\\words.txt");

        this.players = new Player[players];
        this.score   = new int[players];
        for (int i = 0; i < players; i++) {
            this.players[i] = new Player(i, this);
            this.score[i] = 0;
        }

        this.currentPlayer = 0;
    }

    public synchronized void start() {
        for (Player player : players)
            player.start();
    }


    public synchronized void takeTurn(Player player) {
        keepPlayerOrder(player);              // Block any player thread that is not the current player

        if (gameOver()) {                     // Stop any residual threads after the last turn
            notifyAll();
            return;}

        fillLetters(player);                  // Extract letters until the player has 7 again

        turnStartPrompt(player);              // Announcing the current player, displaying available letters

        String word = takeValidInput(player); // Taking and validating input word

        addWord(word);                        // Add the new word

        computeNewScore(player, word);        // Compute the new player score

        setGameOverState();                   // Check if the game is over and
                                              // set gameOver state for all player threads(continue/gameOver)

        incrementPlayer();                    // Set the id of the next player

        if (gameOver()) gameOverPrompt();     // If the game is over, display scores and winning player

        notifyAll();                          // Player turn over. Other threads notified that another player should arrive
    }

    private synchronized void keepPlayerOrder(Player player) {
        while (currentPlayer != player.getPlayerId()) {
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
    private synchronized void turnStartPrompt(Player player) {
        System.out.print("\n=====================================================\n");
        System.out.print("\nPLAYER " + player.getPlayerId() + "\'s TURN\n\nLetters:");
        for (Character c: player.letters)
            System.out.print("\t" + c);
        System.out.print("\n");
    }
    private synchronized String takeValidInput(Player player) {
        String word;
        boolean sufficientLetters;
        Vector<Character> letters = new Vector<>();
        Scanner user = new Scanner(System.in);

        while (true) {
            System.out.print("\nInsert word or \'*\' to pass your turn:\t");    // Initial prompt
            word = user.nextLine();

            if (word.contentEquals("*"))                                    // Skip turn
                break;

            if (!dictionary.containsWord(word)) {                               // Word validation
                System.out.print("Invalid word.\n");
                continue;
            }

            letters.clear();                                                    // Letter inventory validation
            letters.addAll(player.letters);

            sufficientLetters = true;

            for (Character c : word.toCharArray()) {
                if (letters.contains(c)) {
                    letters.remove(c);
                }
                else {
                    System.out.print("Insufficient letters.\n");
                    sufficientLetters = false;
                    continue;
                }
            }

            if (sufficientLetters)
                break;
        }

        player.letters.clear();
        player.letters.addAll(letters);

        return word;
    }
    private synchronized void addWord(String word) {
        board.addWord(word);
    }
    private synchronized int computeWordValue(String word) {
        int value = 0;
        for (Character c : word.toCharArray())
            value += bag.valueOf(c);
        return value;
    }
    private synchronized void computeNewScore(Player player, String word) {
        score[player.getPlayerId()] += computeWordValue(word);
        System.out.print("Awarded " + computeWordValue(word) + " points\n");
        System.out.print("Current score: " + score[player.getPlayerId()] + " points\n");
    }
    private synchronized void fillLetters(Player player) {
        while (player.letters.size() < 7 && bag.lettersLeft() > 0)  // Extract until the player has 7 letters,
            player.letters.add(bag.extractLetter());                // or there are no letters left in the bag

    }
    private synchronized boolean gameOver() {
        if (board.getWords().size() < players.length)
            return false;

        boolean over = true;

        for (int i = 1; i <= players.length; i++)
            if (!board.getWords().elementAt(board.getWords().size() - i).startsWith("*"))
                over = false;
        return over;
    }
    private synchronized void setGameOverState() {
        for (Player player : players)
            player.setGameOver(gameOver());
    }
    private synchronized int winningPlayer() {
        int maxId    = -1;
        int maxScore = -1;

        for (int i = 0; i < players.length; i++) {
            if (score[i] > maxScore) {
                maxScore = score[i];
                maxId = players[i].getPlayerId();
            }
        }

        return maxId;
    }
    private synchronized void incrementPlayer() {

        currentPlayer = (currentPlayer+1) % players.length;
    }
    private synchronized void gameOverPrompt() {
        System.out.print("\n\n\n");

        System.out.print("\t --------------- \n" +
                         "\t|   Game over   |\n" +
                         "\t| Player " + winningPlayer() + " won! |\n" +
                         "\t --------------- \n\n");

        for (int i = 0; i < players.length; i++)
            System.out.print("Player " + players[i].getPlayerId() + ": " + score[i] + " points\n");
    }

}





