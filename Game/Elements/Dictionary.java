package Game.Elements;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Scanner;
import java.util.Vector;

public class Dictionary {

    private Vector<String> words;

    public Dictionary(String dictFile) {
        words = new Vector<>();
        words.add("*");

        try {
            Scanner dictSource =  new Scanner(new BufferedReader(new FileReader(new File(dictFile))));

            while (dictSource.hasNextLine())
                words.add(dictSource.nextLine());
        }
        catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public boolean containsWord(String word) {
        return this.words.contains(word);
    }

}
