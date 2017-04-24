package Game.Elements;

import java.util.Vector;

public class Board {

    private Vector<String> words;

    public Board() {
        this.words = new Vector<>();
    }

    public void addWord(String word) {
        this.words.add(word);
        System.out.print("Added word \'" + word + "\'.\n");
    }

    public Vector<String> getWords() {
        return words;
    }
}