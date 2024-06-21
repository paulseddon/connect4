package org.test;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("[R/r] or [Y/y] to play first?");
        Connect4.State first = null;
        do {
            String in = scanner.nextLine();
            if (in.matches("[Rr]")) {
                first = Connect4.State.RED_TO_PLAY;
            } else if (in.matches("[Yy]")) {
                first = Connect4.State.YELLOW_TO_PLAY;
            } else {
                System.out.println("Invalid input");
            }
        } while(first == null);

        Connect4 game = new Connect4(first);
        do {
            game.print();
            Connect4.State currentState = game.getState();
            System.out.println(currentState + ": next column?");
            int col = scanner.nextInt();
            game.placeToken(col, currentState == Connect4.State.RED_TO_PLAY ? Token.RED: Token.YELLOW);

        } while(game.getState() == Connect4.State.RED_TO_PLAY || game.getState() == Connect4.State.YELLOW_TO_PLAY);

        game.print();
        System.out.println("Game over: " + game.getState());
    }
}