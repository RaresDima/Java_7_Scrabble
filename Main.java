import Game.Game;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner user = new Scanner(System.in);
        System.out.print("Players: ");

        new Game(user.nextInt()).start();
    }
}
