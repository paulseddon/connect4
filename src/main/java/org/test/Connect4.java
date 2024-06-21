package org.test;

import java.util.Arrays;

/*
 * Standard Connect 4 game.
 */
public class Connect4 {

    public static final int ROWS = 6;
    public static final int COLUMNS = 7;

    private final Token[][] board = new Token[ROWS][COLUMNS];

    private State state;

    public Connect4(State initialState) {
        for(int x = 0; x < ROWS; x++) {
            // should be nulls, but let's fill in case we want to represent empty slot differently
            Arrays.fill(board[x], null);
        }

        if(initialState != State.RED_TO_PLAY && initialState != State.YELLOW_TO_PLAY) {
            throw new RuntimeException("Invalid initial state");
        }

        state = initialState;
    }

    public void print() {
        for(int r = ROWS - 1; r >= 0; r--) {
            System.out.println();
            for(int c = 0; c < COLUMNS; c++) {
                Token token = board[r][c];
                System.out.print(token == null ? "[ ]" : token.toString());
            }
        }
        System.out.println();
    }

    public State placeToken(int column, Token token) {
        if(state != State.RED_TO_PLAY && state != State.YELLOW_TO_PLAY) {
            System.out.println("Game finished");
            return state;
        }

        if((state == State.RED_TO_PLAY && token == Token.YELLOW) ||
                (state == State.YELLOW_TO_PLAY && token == Token.RED)) {
            System.out.println("Wrong colour");
            return state;
        }

        if(column < 0 || column > COLUMNS - 1) {
            System.out.println("Column does not exist");
            return state;
        }

        // find next free row in column
        boolean placed = false;
        for(int row = 0; row < ROWS; row++) {
            if (board[row][column] == null) {
                board[row][column] = token;
                placed = true;
                break;
            }
        }
        if(!placed) {
            System.out.println("Cannot place in column");
            return state;
        }

        // check for win
        boolean win = checkForWin();

        // check board is not full
        if(!win && checkFullBoard()) {
            state = State.DRAW;
            return state;
        }

        // move to next state
        state = nextState(state, win);

        return state;
    }

    private State nextState(State currentState, boolean win) {
        if(currentState == State.RED_TO_PLAY) {
            if(win) {
                return State.RED_WINS;
            } else {
                return State.YELLOW_TO_PLAY;
            }
        } else if(currentState == State.YELLOW_TO_PLAY){
            if(win) {
                return State.YELLOW_WINS;
            } else {
                return State.RED_TO_PLAY;
            }
        } else {
            throw new RuntimeException("Unexpected state: " + currentState);
        }
    }

    private boolean checkFullBoard() {
        boolean full = true;
        for (int c = 0; c < COLUMNS; c++) {
            if (!isFull(c)) {
                full = false;
                break;
            }
        }
        return full;
    }

    public boolean isFull(int column) {
        return board[ROWS - 1][column] != null;
    }

    public State getState() {
        return state;
    }

    private boolean checkForWin() {
        if (checkHorizontal()) return true;

        if (checkVertical()) return true;

        if (checkDiagonalBottomLeft()) return true;

        if (checkDiagonalBottomRight()) return true;

        return false;
    }

    private boolean checkHorizontal() {
        // check horizontal
        for(int r = 0; r < ROWS; r++) {
            for(int c = 0; c < 4; c++) {
                Token token = board[r][c];
                if(token == null) {
                    continue;
                }
                // check next 4 are same
                if (isConnect4(r, c, 0, 1, token)) {
                    return true;
                }
            }
        }
        return false;
    }

    private boolean checkVertical() {
        // check vertical
        for(int c = 0; c < COLUMNS; c++) {
            for(int r = 0; r < 3; r++) {
                Token token = board[r][c];
                if(token == null) {
                    continue;
                }
                // check next 4 are same
                if (isConnect4(r, c, 1, 0, token)) {
                    return true;
                }
            }
        }
        return false;
    }

    private boolean checkDiagonalBottomLeft() {
        // check diagonal - bottom left
        for(int c = 0; c < 4; c++) {
            for(int r = 0; r < 3; r++) {
                Token token = board[r][c];
                if(token == null) {
                    continue;
                }
                // check next 4 are same
                if (isConnect4(r, c, 1, 1, token)) {
                    return true;
                }
            }
        }
        return false;
    }

    private boolean checkDiagonalBottomRight() {
        // check diagonal - bottom right
        for(int c = COLUMNS - 1; c > 2; c--) {
            for(int r = 0; r < 3; r++) {
                Token token = board[r][c];
                if(token == null) {
                    continue;
                }
                // check next 4 are same
                if (isConnect4(r, c, 1, -1, token)) {
                    return true;
                }
            }
        }
        return false;
    }

    private boolean isConnect4(int row, int col, int rowDelta, int colDelta, Token token) {
        boolean win = true;
        for(int i = 0; i < 4; i++) {
            if(board[row + (i * rowDelta)][col + (i * colDelta)] != token) {
                win = false;
                break;
            }
        }
        return win;
    }

    public enum State {
        RED_TO_PLAY,
        YELLOW_TO_PLAY,
        RED_WINS,
        YELLOW_WINS,
        DRAW;
    }
}

