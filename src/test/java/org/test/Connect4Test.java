package org.test;

import org.junit.jupiter.api.Test;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

public class Connect4Test {

    @Test
    public void testNegativeColumn() {
        Connect4 game = new Connect4(Connect4.State.RED_TO_PLAY);
        Connect4.State state = game.placeToken(-1, Token.RED);

        assertEquals(Connect4.State.RED_TO_PLAY, state);
    }

    @Test
    public void testOutOfBoundsColumn() {
        Connect4 game = new Connect4(Connect4.State.RED_TO_PLAY);
        Connect4.State state = game.placeToken(-1, Token.RED);

        assertEquals(Connect4.State.RED_TO_PLAY, state);
    }

    @Test
    public void testFullColumn() {
        Connect4 game = new Connect4(Connect4.State.RED_TO_PLAY);
        game.placeToken(0, Token.RED);
        game.placeToken(0, Token.YELLOW);
        game.placeToken(0, Token.RED);
        game.placeToken(0, Token.YELLOW);
        game.placeToken(0, Token.RED);
        game.placeToken(0, Token.YELLOW);
        Connect4.State state = game.placeToken(0, Token.RED);

        game.print();

        assertEquals(Connect4.State.RED_TO_PLAY, state);
    }


    @Test
    public void testHorizontalWin() {
        Connect4 game = new Connect4(Connect4.State.RED_TO_PLAY);
        game.placeToken(2, Token.RED);
        game.placeToken(2, Token.YELLOW);
        game.placeToken(3, Token.RED);
        game.placeToken(3, Token.YELLOW);
        game.placeToken(4, Token.RED);
        game.placeToken(4, Token.YELLOW);
        game.placeToken(6, Token.RED);
        game.placeToken(5, Token.YELLOW);
        game.placeToken(0, Token.RED);
        Connect4.State finalState = game.placeToken(5, Token.YELLOW);

        game.print();

        assertEquals(Connect4.State.YELLOW_WINS, finalState);
    }

    @Test
    public void testVerticalWin() {
        Connect4 game = new Connect4(Connect4.State.RED_TO_PLAY);
        game.placeToken(2, Token.RED);
        game.placeToken(3, Token.YELLOW);
        game.placeToken(2, Token.RED);
        game.placeToken(4, Token.YELLOW);
        game.placeToken(2, Token.RED);
        game.placeToken(5, Token.YELLOW);
        Connect4.State finalState = game.placeToken(2, Token.RED);

        game.print();

        assertEquals(Connect4.State.RED_WINS, finalState);
    }

    @Test
    public void testDiagonalWinFromBottomLeft() {
        Connect4 game = new Connect4(Connect4.State.RED_TO_PLAY);
        game.placeToken(0, Token.RED);
        game.placeToken(1, Token.YELLOW);
        game.placeToken(1, Token.RED);
        game.placeToken(2, Token.YELLOW);
        game.placeToken(2, Token.RED);
        game.placeToken(3, Token.YELLOW);
        game.placeToken(2, Token.RED);
        game.placeToken(5, Token.YELLOW);
        game.placeToken(3, Token.RED);
        game.placeToken(3, Token.YELLOW);
        Connect4.State finalState = game.placeToken(3, Token.RED);

        game.print();

        assertEquals(Connect4.State.RED_WINS, finalState);
    }

    @Test
    public void testDiagonalWinFromBottomRight() {
        Connect4 game = new Connect4(Connect4.State.RED_TO_PLAY);
        game.placeToken(3, Token.RED);
        game.placeToken(0, Token.YELLOW);
        game.placeToken(2, Token.RED);
        game.placeToken(0, Token.YELLOW);
        game.placeToken(2, Token.RED);
        game.placeToken(0, Token.YELLOW);
        game.placeToken(0, Token.RED);
        game.placeToken(1, Token.YELLOW);
        game.placeToken(1, Token.RED);
        game.placeToken(5, Token.YELLOW);
        Connect4.State finalState = game.placeToken(1, Token.RED);

        game.print();

        assertEquals(Connect4.State.RED_WINS, finalState);
    }

    @Test
    public void testFullBoard() {
        // randomly fill board with no winner
        Connect4 game;
        do {
            game = randomBoard();
        } while(game.getState() == Connect4.State.RED_WINS || game.getState() == Connect4.State.YELLOW_WINS);

        game.print();

        assertEquals(Connect4.State.DRAW, game.getState());
    }

    private Connect4 randomBoard() {
        Connect4 game = new Connect4(Connect4.State.RED_TO_PLAY);
        Random rand = new Random();
        int placed = 0;
        boolean red = true;
        Connect4.State state;
        do {
            int col = rand.nextInt(7);
            // check space in column
            if (game.isFull(col)) {
                continue;
            }
            state = game.placeToken(col, red ? Token.RED : Token.YELLOW);
            // check if we created a win
            if (state == Connect4.State.RED_WINS || state == Connect4.State.YELLOW_WINS) {
                break;
            }
            red = !red;
            placed++;
        } while (placed < Connect4.ROWS * Connect4.COLUMNS);

        return game;
    }
}
