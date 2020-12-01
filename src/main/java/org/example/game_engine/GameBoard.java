package org.example.game_engine;

import org.example.App;
import org.example.IO_utils.BReader;
import org.example.IO_utils.BWriter;
import org.example.IO_utils.ScannerInput;
import org.example.exceptions.ColumnFullException;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class GameBoard implements GameEngine {

    private ScannerInput scannerInput = new ScannerInput();
    BWriter bufferWriter = new BWriter();
    BReader bufferReader = new BReader();
    public String replay = "\u001B[36mREPLAY OF LAST ROUND:";

    private String[][] gameBoard;
    private boolean playerOne;
    private String previousMove;
    private int roundsCounter;
    private int numberOfRounds;
    private int pOneWonRounds;
    private int pTwoWonRounds;
    private boolean isTesting;

    public GameBoard() {
        this.roundsCounter = 0;
        this.previousMove = "";
        this.isTesting = false;
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
        roundsCounter = 0;
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
    }

    public void play() {
        System.out.println("Round "+(++roundsCounter));
        while (numberOfRounds > 0) {
            System.out.print(playerOne ? "\nPlayerOne, " : "\nPlayerTwo, ");
            System.out.print("Choose your play: ");
            // Continue round
            makeAMove(scannerInput.getUserInput().nextInt());
            printCurrentBoard();
        }
    }

    public void printCurrentBoard() {

//         write currentBoard to file.
//        bufferWriter.writeToFile(gameBoard);
        /*
        replay = replay.concat(Arrays
                .stream(gameBoard)
                .map(Arrays::toString)
                .collect(Collectors.joining(System.lineSeparator()))) + "\n\t\n";
        */
        replay = replay.concat("\n┌───┬───┬───┬───┬───┬───┬───┐\n");

        System.out.println("\n┌───┬───┬───┬───┬───┬───┬───┐");
        for (int i = gameBoard.length-1; i >= 0; i--) {
            replay = replay.concat("│ ");
            System.out.print("│ ");
            for (int j = 0; j < gameBoard[i].length ; j++) {
                replay = replay.concat(gameBoard[i][j] + " │ ");
                System.out.print(gameBoard[i][j] + " │ ");
            }
            replay = replay.concat("\n");
            System.out.println();
            if (i >= 1) {
                replay = replay.concat("├───┼───┼───┼───┼───┼───┼───┤\n");
                System.out.println("├───┼───┼───┼───┼───┼───┼───┤");
            } else {
                replay = replay.concat("└───┴───┴───┴───┴───┴───┴───┘\n");
                System.out.println("└───┴───┴───┴───┴───┴───┴───┘");
            }
        }
    }

    public void printWinner(String currentPlayer) {
        replay = replay.concat("\n┌───────────────────────┐\n");
        replay = currentPlayer.contentEquals("X") ? replay.concat("├─── PlayerOne Wins! ───┤\n") : replay.concat("├─── PlayerTwo Wins! ───┤\n");
        replay = replay.concat("└───────────────────────┘\n");
        System.out.println("\n┌───────────────────────┐");
        System.out.println(currentPlayer.contentEquals("X") ? "├─── PlayerOne Wins! ───┤" : "├─── PlayerTwo Wins! ───┤");
        System.out.println("└───────────────────────┘");
        printCurrentBoard();
        replay = replay.concat("\u001B[0m");

        if (currentPlayer.contentEquals("X")) pOneWonRounds++;
        else pTwoWonRounds++;

        System.out.println("Current score: " + "PlayerOne: " + pOneWonRounds + " PlayerTwo: " + pTwoWonRounds);
        resetGameBoard();
        if (numberOfRounds == 0 && !isTesting) {
            App.startApp();
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
                    if (!isTesting){
                        printWinner(currentPlayer);
                    }
                    return currentPlayer.equals("X") ? "Player One" : "Player Two";
                }
            }
            streak = 0;
            //Horizontally left
            for (int i = 1; i < 4; i++){
                if (col >= 3 && gameBoard[row][col-i].equals(currentPlayer)) streak++;
                if (streak == 3) {
                    // every move, decrease amount of given rounds
                    numberOfRounds--;
                    if (!isTesting){
                        printWinner(currentPlayer);
                    }
                    return currentPlayer.equals("X") ? "Player One" : "Player Two";
                }
            }
            streak = 0;

            //Horizontally right
            for (int i = 1; i < 4; i++){
                if (col <= 3 && gameBoard[row][col+i].equals(currentPlayer)) streak++;
                if (streak == 3) {
                    // every move, decrease amount of given rounds
                    numberOfRounds--;
                    if (!isTesting){
                        printWinner(currentPlayer);
                    }
                    return currentPlayer.equals("X") ? "Player One" : "Player Two";
                }
            }
            streak = 0;

        //Diagonally down and to the right
            for (int i = 1; i < 4; i++){
                if ((row >= 3 && col <= 3) && (gameBoard[row-i][col+i].equals(currentPlayer))) streak++;
                if (streak == 3) {
                    // every move, decrease amount of given rounds
                    numberOfRounds--;
                    if (!isTesting){
                        printWinner(currentPlayer);
                    }
                    return currentPlayer.equals("X") ? "Player One" : "Player Two";
                }
            }
        streak = 0;


        //Diagonally down and to the left
            for (int i = 1; i < 4; i++){
                if ((row >= 3 && col >= 3)&&gameBoard[row-i][col-i].equals(currentPlayer)) streak++;
                if (streak == 3) {
                    // every move, decrease amount of given rounds
                    numberOfRounds--;
                    if (!isTesting){
                        printWinner(currentPlayer);
                    }
                    return currentPlayer.equals("X") ? "Player One" : "Player Two" ;
                }
            }

        streak = 0;


        //Diagonally up and to the right
            for (int i = 1; i < 4; i++){
              if ((row < 3 && col <= 3)&&gameBoard[row+i][col+i].equals(currentPlayer)) streak++;
              if (streak == 3) {
                  // every move, decrease amount of given rounds
                  numberOfRounds--;
                  if (!isTesting){
                      printWinner(currentPlayer);
                  }
                  return currentPlayer.equals("X") ? "Player One" : "Player Two" ;
              }
            }

        streak = 0;

        //Diagonally up and to the left
            for (int i = 1; i < 4; i++){
               if ((row < 3 && col >= 3)&&gameBoard[row+i][col-i].equals(currentPlayer)) streak++;
               if (streak == 3) {
                   // every move, decrease amount of given rounds
                   numberOfRounds--;
                   if (!isTesting){
                       printWinner(currentPlayer);
                   }
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

    public void setTesting(boolean testing) {
        isTesting = testing;
    }
}
