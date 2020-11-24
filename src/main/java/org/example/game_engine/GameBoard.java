package org.example.game_engine;

import org.example.exceptions.ColumnFullException;

public class GameBoard implements GameEngine {

    private String[][] gameBoard;
    private boolean playerOne;

    public GameBoard() {
        playerOne = Math.floor(Math.random() * 2) == 0;
        this.gameBoard = new String[][]{
                {" ", " ", " ", " ", " ", " ", " "},
                {" ", " ", " ", " ", " ", " ", " "},
                {" ", " ", " ", " ", " ", " ", " "},
                {" ", " ", " ", " ", " ", " ", " "},
                {" ", " ", " ", " ", " ", " ", " "},
                {" ", " ", " ", " ", " ", " ", " "}};
    }

    @Override
    public String[][] makeAMove(int col) {
        if (col > gameBoard[0].length-1 || col < 0) throw new IndexOutOfBoundsException();
        if (!gameBoard[5][col].equals(" ")) throw new ColumnFullException("This column is full");
        for (int i = 0; i < gameBoard.length; i++) {
            if (gameBoard[i][col].equals(" ")) {
                gameBoard[i][col] = playerOne ? "X" : "O";
                playerOne = !playerOne;
                break;
            }
        }
        return gameBoard;
    }

    @Override
    public String checkWinner() {
        return null;
    }

    @Override
    public void viewReplay() {

    }

    public void setPlayerOne(boolean playerOne) {
        this.playerOne = playerOne;
    }
}
