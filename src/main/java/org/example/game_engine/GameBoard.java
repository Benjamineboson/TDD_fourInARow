package org.example.game_engine;

import org.example.exceptions.ColumnFullException;

public class GameBoard implements GameEngine {

    private String[][] gameBoard;
    private boolean playerOne;
    private String previousMove;
    private int movesCounter;

    public GameBoard() {
        this.movesCounter = 0;
        this.previousMove = "";
        this.playerOne = Math.floor(Math.random() * 2) == 0;
        this.gameBoard = new String[][]{
                {" ", " ", " ", " ", " ", " ", " "},
                {" ", " ", " ", " ", " ", " ", " "},
                {" ", " ", " ", " ", " ", " ", " "},
                {" ", " ", " ", " ", " ", " ", " "},
                {" ", " ", " ", " ", " ", " ", " "},
                {" ", " ", " ", " ", " ", " ", " "}};


//                "╔═══╦═══╦═══╦═══╦═══╦═══╦═══╗
//                "║50 ║   ║   ║   ║   ║   ║   ║
//                "╠═══╬═══╬═══╬═══╬═══╬═══╬═══╣
//                "║40 ║   ║   ║   ║   ║   ║   ║
//                "╠═══╬═══╬═══╬═══╬═══╬═══╬═══╣
//                "║30 ║   ║   ║   ║   ║   ║   ║
//                "╠═══╬═══╬═══╬═══╬═══╬═══╬═══╣
//                "║20 ║   ║   ║   ║   ║   ║   ║
//                "╠═══╬═══╬═══╬═══╬═══╬═══╬═══╣
//                "║10 ║   ║   ║   ║   ║   ║   ║
//                "╠═══╬═══╬═══╬═══╬═══╬═══╬═══╣
//                "║00 ║01 ║02 ║03 ║04 ║05 ║06 ║
//                "╚═══╩═══╩═══╩═══╩═══╩═══╩═══╝

    }

    @Override
    public String[][] makeAMove(int col) {
        if (col > gameBoard[0].length-1 || col < 0) throw new IndexOutOfBoundsException();
        if (!gameBoard[5][col].equals(" ")) throw new ColumnFullException("This column is full");

        for (int i = 0; i < gameBoard.length; i++) {
            if (gameBoard[i][col].equals(" ")) {
                gameBoard[i][col] = playerOne ? "X" : "O";
                previousMove = ""+i+""+col;
                playerOne = !playerOne;
                movesCounter++;
                break;
            }
        }

        checkWinner();
        return gameBoard;
    }

    @Override
    public String checkWinner() {
        //previousMoves
        int row = Integer.parseInt(previousMove.substring(0,1));
        int col = Integer.parseInt(previousMove.substring(1));

        //If playerOne made last move, it's now player two's turn.
        String currentPlayer = playerOne ? "O" : "X";
        int streak = 0;

            // Vertically down
            for (int i = 1; i < 4; i++){
                if (row >= 3 && gameBoard[row-i][col].equals(currentPlayer)) streak++;
                if (streak == 3) return currentPlayer.equals("X") ? "Player One" : "Player Two";
            }

            //Horizontally left
            for (int i = 1; i < 4; i++){
                if (col >= 3 && gameBoard[row][col-i].equals(currentPlayer)) streak++;
                if (streak == 3) return currentPlayer.equals("X") ? "Player One" : "Player Two";
            }

            //Horizontally right
            for (int i = 1; i < 4; i++){
                if (col <= 3 && gameBoard[row][col+i].equals(currentPlayer)) streak++;
                if (streak == 3) return currentPlayer.equals("X") ? "Player One" : "Player Two";
            }

            //Diagonally down and to the right
            for (int i = 1; i < 4; i++){
                if ((row >= 3 && col <= 3) && (gameBoard[row-i][col+i].equals(currentPlayer))) streak++;
                if (streak == 3) return currentPlayer.equals("X") ? "Player One" : "Player Two";
            }

            //Diagonally down and to the left
            for (int i = 1; i < 4; i++){
                if ((row >= 3 && col >= 3)&&gameBoard[row-i][col-i].equals(currentPlayer)) streak++;
                if (streak == 3) return currentPlayer.equals("X") ? "Player One" : "Player Two" ;
            }

            //Diagonally up and to the right
            for (int i = 1; i < 4; i++){
              if ((row < 3 && col <= 3)&&gameBoard[row+i][col+i].equals(currentPlayer)) streak++;
              if (streak == 3) return currentPlayer.equals("X") ? "Player One" : "Player Two" ;
            }

            //Diagonally up and to the left
            for (int i = 1; i < 4; i++){
               if ((row < 3 && col >= 3)&&gameBoard[row+i][col-i].equals(currentPlayer)) streak++;
               if (streak == 3) return currentPlayer.equals("X") ? "Player One" : "Player Two" ;
            }

            return "Draw";
    }

    @Override
    public void viewReplay() {

    }

    public void setPlayerOne(boolean playerOne) {
        this.playerOne = playerOne;
    }
}
