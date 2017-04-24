package Game.Elements;

import java.util.Random;
import java.util.Vector;

public class Bag {

    private Vector<Character> letters;

    public Bag() {
        letters = new Vector<>();
        addLetters();
    }

    private void addLetters() {
        for (int i = 0; i < 1; i++) {
            letters.add('h');
            letters.add('j');
            letters.add('x');
            letters.add('z');
        }

        for (int i = 0; i < 2; i++) {
            letters.add('f');
            letters.add('v');
            letters.add('b');
            letters.add('g');
        }


        for (int i = 0; i < 3; i++) {
            letters.add('m');
        }

        for (int i = 0; i < 4; i++) {
            letters.add('l');
            letters.add('d');
            letters.add('p');
        }

        for (int i = 0; i < 5; i++) {
            letters.add('c');
            letters.add('o');
            letters.add('s');
        }

        for (int i = 0; i < 6; i++) {
            letters.add('n');
            letters.add('u');
        }

        for (int i = 0; i < 7; i++) {
            letters.add('r');
            letters.add('t');
        }

        for (int i = 0; i < 9; i++)
            letters.add('e');

        for (int i = 0; i < 10; i++)
            letters.add('i');

        for (int i = 0; i < 11; i++)
            letters.add('a');
    }
    public int valueOf(Character c) {
        switch (c) {
            case 'a':
                return 1;
            case 'b':
                return 9;
            case 'c':
                return 1;
            case 'd':
                return 2;
            case 'e':
                return 1;
            case 'f':
                return 8;
            case 'g':
                return 9;
            case 'h':
                return 10;
            case 'i':
                return 1;
            case 'j':
                return 10;
            case 'l':
                return	1;
            case 'm':
                return 4;
            case 'n':
                return 1;
            case 'o':
                return 1;
            case 'p':
                return 2;
            case 'r':
                return 1;
            case 's':
                return 1;
            case 't':
                return 1;
            case 'u':
                return 1;
            case 'v':
                return 8;
            case 'x':
                return 10;
            case 'z':
                return 10;
            default:
                return 0;
        }
    }
    public Character extractLetter() {
        Random randGen = new Random();
        Character c = letters.elementAt(randGen.nextInt(letters.size()));
        letters.remove(c);

        return c;
    }
    public int lettersLeft() {
        return letters.size();
    }
}











