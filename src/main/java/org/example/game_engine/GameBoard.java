package org.example.game_engine;

import org.example.App;
import org.example.IO_utils.BReader;
import org.example.IO_utils.BWriter;
import org.example.IO_utils.ScannerInput;
import org.example.exceptions.ColumnFullException;

import java.io.*;

public class GameBoard implements GameEngine {

    private ScannerInput scannerInput = new ScannerInput();
    BWriter bufferWriter = new BWriter();
    BReader bufferReader = new BReader();

    private String[][] gameBoard;
    private boolean playerOne;
    private String previousMove;
    private int numberOfRounds;
    private int roundsCounter;
    private int playerOneWinStreak;
    private int playerTwoWinStreak;

    public GameBoard() {
        this.previousMove = "";
        this.playerOne = Math.floor(Math.random() * 2) == 0;
        this.numberOfRounds = 0;
        this.roundsCounter = 0;
        this.playerOneWinStreak = 0;
        this.playerTwoWinStreak = 0;
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
        roundsCounter = 1;
        playerTwoWinStreak = 0;
        playerOneWinStreak = 0;
        play();
    }

    public void play(){
        int currentRound = numberOfRounds;
        while (numberOfRounds > 0) {
            if (currentRound > numberOfRounds){
                currentRound--;
                ++roundsCounter;
                resetBoard();
            }
            System.out.println("[ ROUND "+roundsCounter+" ]");
            System.out.print(playerOne ? "\nPlayerOne, " : "\nPlayerTwo, ");
            System.out.print("Choose your play: ");
            makeAMove(scannerInput.getUserInput().nextInt());
        }
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
                break;
            }
        }
        printCurrentBoard();
        if (!checkWinner().equals("Draw")){
            numberOfRounds--;
            printWinner(checkWinner());
        }
        return gameBoard;
    }

    public void printWinner(String winner) {
        System.out.println("\n┌───────────────────────┐");
        System.out.println(winner.equals("Player One") ? "├─── PlayerOne Wins! ───┤" : "├─── PlayerTwo Wins! ───┤");
        System.out.println("└───────────────────────┘");
        printCurrentBoard();

        if (winner.equals("Player One")) playerOneWinStreak++;
        else playerTwoWinStreak++;

        System.out.println("Current score: " + "PlayerOne: " + playerOneWinStreak + " PlayerTwo: " + playerTwoWinStreak);
        if (numberOfRounds == 0) {
            App.startApp();
            roundsCounter = 0;
        }
    }

    private void resetBoard() {
        this.playerOne = Math.floor(Math.random() * 2) == 0;
        gameBoard = new String[][]{
                {" ", " ", " ", " ", " ", " ", " "},
                {" ", " ", " ", " ", " ", " ", " "},
                {" ", " ", " ", " ", " ", " ", " "},
                {" ", " ", " ", " ", " ", " ", " "},
                {" ", " ", " ", " ", " ", " ", " "},
                {" ", " ", " ", " ", " ", " ", " "}};
    }

    public void printCurrentBoard() {
        bufferWriter.writeToFile(gameBoard);

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

    @Override
    public String checkWinner() {
        int row = Integer.parseInt(previousMove.substring(0,1));
        int col = Integer.parseInt(previousMove.substring(1));

        String currentPlayer = playerOne ? "O" : "X";
        int streak = 0;

            // Vertically down
            for (int i = 1; i < 4; i++){
                if (row >= 3 && gameBoard[row-i][col].equals(currentPlayer)) streak++;
                if (streak == 3) {

                    return currentPlayer.equals("X") ? "Player One" : "Player Two";
                }
            }
            streak = 0;
            //Horizontally left
            for (int i = 1; i < 4; i++){
                if (col >= 3 && gameBoard[row][col-i].equals(currentPlayer)) streak++;
                if (streak == 3) {
                    return currentPlayer.equals("X") ? "Player One" : "Player Two";
                }
            }
            streak = 0;

            //Horizontally right
            for (int i = 1; i < 4; i++){
                if (col <= 3 && gameBoard[row][col+i].equals(currentPlayer)) streak++;
                if (streak == 3) {
                    return currentPlayer.equals("X") ? "Player One" : "Player Two";
                }
            }
            streak = 0;

        //Diagonally down and to the right
            for (int i = 1; i < 4; i++){
                if ((row >= 3 && col <= 3) && (gameBoard[row-i][col+i].equals(currentPlayer))) streak++;
                if (streak == 3) {
                    return currentPlayer.equals("X") ? "Player One" : "Player Two";
                }
            }
            streak = 0;


        //Diagonally down and to the left
            for (int i = 1; i < 4; i++){
                if ((row >= 3 && col >= 3)&&gameBoard[row-i][col-i].equals(currentPlayer)) streak++;
                if (streak == 3) {
                    return currentPlayer.equals("X") ? "Player One" : "Player Two" ;
                }
            }

        streak = 0;


        //Diagonally up and to the right
            for (int i = 1; i < 4; i++){
              if ((row < 3 && col <= 3)&&gameBoard[row+i][col+i].equals(currentPlayer)) streak++;
              if (streak == 3) {
                  return currentPlayer.equals("X") ? "Player One" : "Player Two" ;
              }
            }

        streak = 0;

        //Diagonally up and to the left
            for (int i = 1; i < 4; i++){
               if ((row < 3 && col >= 3)&&gameBoard[row+i][col-i].equals(currentPlayer)) streak++;
               if (streak == 3) {
                   return currentPlayer.equals("X") ? "Player One" : "Player Two" ;
               }
            }
            return "Draw";
    }

    @Override
    public void viewReplay() {
//        bufferReader.readFromFile().forEach(System.out::println);
//        resetGameBoard();
    }

    public void setPlayerOne(boolean playerOne) {
        this.playerOne = playerOne;
    }


}
