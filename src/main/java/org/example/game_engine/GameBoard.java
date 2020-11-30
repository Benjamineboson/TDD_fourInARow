package org.example.game_engine;

import org.example.App;
import org.example.IO_utils.ScannerInput;
import org.example.exceptions.ColumnFullException;

public class GameBoard implements GameEngine {

    private ScannerInput scannerInput = new ScannerInput();

    private String[][] gameBoard;
    private boolean playerOne;
    private String previousMove;
    private int movesCounter;
    private int numberOfRounds;
    private int pOneWonRounds;
    private int pTwoWonRounds;

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
    }

    public void setNumberOfRounds() {
        System.out.print("Choose number of rounds: ");
        numberOfRounds = scannerInput.getUserInput().nextInt();
        play();
    }

    public void resetGameBoard() {
        this.playerOne = Math.floor(Math.random() * 2) == 0;
        gameBoard = new String[][]{
                {" ", " ", " ", " ", " ", " ", " "},
                {" ", " ", " ", " ", " ", " ", " "},
                {" ", " ", " ", " ", " ", " ", " "},
                {" ", " ", " ", " ", " ", " ", " "},
                {" ", " ", " ", " ", " ", " ", " "},
                {" ", " ", " ", " ", " ", " ", " "}};
        play();
    }

    public void play() {
        System.out.println("Current score: " + "PlayerOne: " + pOneWonRounds + " PlayerTwo: " + pTwoWonRounds);
        while (numberOfRounds > 0) {
            System.out.print(playerOne ? "\nPlayerOne, " : "\nPlayerTwo, ");
            System.out.print("Choose your play: ");
            makeAMove(scannerInput.getUserInput().nextInt());
            printCurrentBoard();
        }
    }

    public void printCurrentBoard() {
        System.out.println("\n┌───┬───┬───┬───┬───┬───┬───┐");
        for (int i = gameBoard.length-1; i >= 0; i--) {
            System.out.print("│ ");
            for (int j = 0; j < gameBoard[i].length ; j++) {
                System.out.print(gameBoard[i][j] + " │ ");
            }
            System.out.println();
            if (i >= 1) {
                System.out.println("├───┼───┼───┼───┼───┼───┼───┤");
            } else {
                System.out.println("└───┴───┴───┴───┴───┴───┴───┘");
            }
        }
    }

    public void printWinner(String currentPlayer) {
        System.out.println("\n┌───────────────────────┐");
        System.out.println(currentPlayer.contentEquals("X") ? "├─── PlayerOne Wins! ───┤" : "├─── PlayerTwo Wins! ───┤");
        System.out.println("└───────────────────────┘");
        printCurrentBoard();

        if (currentPlayer.contentEquals("X")) {
            pOneWonRounds++;
        } else {
            pTwoWonRounds++;
        }

        if ((numberOfRounds > 0)) {
            resetGameBoard();
        } else {
            App.startApp();
        }

        System.out.println("Continue by pressing enter...");

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
                if (streak == 3) {
                    // every move, decrease amount of given rounds
                    numberOfRounds--;
                    printWinner(currentPlayer);
                    return currentPlayer.equals("X") ? "Player One" : "Player Two";
                }
            }

            //Horizontally left
            for (int i = 1; i < 4; i++){
                if (col >= 3 && gameBoard[row][col-i].equals(currentPlayer)) streak++;
                if (streak == 3) {
                    // every move, decrease amount of given rounds
                    numberOfRounds--;
                    printWinner(currentPlayer);
                    return currentPlayer.equals("X") ? "Player One" : "Player Two";
                }
            }

            //Horizontally right
            for (int i = 1; i < 4; i++){
                if (col <= 3 && gameBoard[row][col+i].equals(currentPlayer)) streak++;
                if (streak == 3) {
                    // every move, decrease amount of given rounds
                    numberOfRounds--;
                    printWinner(currentPlayer);
                    return currentPlayer.equals("X") ? "Player One" : "Player Two";
                }
            }

            //Diagonally down and to the right
            for (int i = 1; i < 4; i++){
                if ((row >= 3 && col <= 3) && (gameBoard[row-i][col+i].equals(currentPlayer))) streak++;
                if (streak == 3) {
                    // every move, decrease amount of given rounds
                    numberOfRounds--;
                    printWinner(currentPlayer);
                    return currentPlayer.equals("X") ? "Player One" : "Player Two";
                }
            }

            //Diagonally down and to the left
            for (int i = 1; i < 4; i++){
                if ((row >= 3 && col >= 3)&&gameBoard[row-i][col-i].equals(currentPlayer)) streak++;
                if (streak == 3) {
                    // every move, decrease amount of given rounds
                    numberOfRounds--;
                    printWinner(currentPlayer);
                    return currentPlayer.equals("X") ? "Player One" : "Player Two" ;
                }
            }

            //Diagonally up and to the right
            for (int i = 1; i < 4; i++){
              if ((row < 3 && col <= 3)&&gameBoard[row+i][col+i].equals(currentPlayer)) streak++;
              if (streak == 3) {
                  // every move, decrease amount of given rounds
                  numberOfRounds--;
                  printWinner(currentPlayer);
                  return currentPlayer.equals("X") ? "Player One" : "Player Two" ;
              }
            }

            //Diagonally up and to the left
            for (int i = 1; i < 4; i++){
               if ((row < 3 && col >= 3)&&gameBoard[row+i][col-i].equals(currentPlayer)) streak++;
               if (streak == 3) {
                   // every move, decrease amount of given rounds
                   numberOfRounds--;
                   printWinner(currentPlayer);
                   return currentPlayer.equals("X") ? "Player One" : "Player Two" ;
               }
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
