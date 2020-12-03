package org.example.game_engine;

import org.example.App;
import org.example.IO_utils.BReader;
import org.example.IO_utils.BWriter;
import org.example.IO_utils.ScannerInput;
import org.example.exceptions.ColumnFullException;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class GameBoard implements GameEngine {

    private ScannerInput scannerInput = new ScannerInput();
    private final BWriter bufferWriter = new BWriter();
    private final BReader bufferReader = new BReader();

    private String[][] gameBoard;
    private boolean playerOne;
    private String previousMove;
    private int numberOfRounds;
    private int roundsCounter;
    private int movesCounter;
    private int playerOneWinStreak;
    private int playerTwoWinStreak;

    public GameBoard() {
        this.previousMove = "";
        this.playerOne = Math.floor(Math.random() * 2) == 0;
        this.numberOfRounds = 0;
        this.roundsCounter = 0;
        this.playerOneWinStreak = 0;
        this.playerTwoWinStreak = 0;
        this.movesCounter = 0;

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
        resetBoard();
        play();
    }

    public void play() {
        int currentRound = numberOfRounds;
        while (numberOfRounds > 0) {
            if (currentRound > numberOfRounds) {
                currentRound--;
                ++roundsCounter;
                resetBoard();
            }
            System.out.println("[ ROUND " + roundsCounter + " ]");
            System.out.print(playerOne ? "\nPlayerOne, " : "\nPlayerTwo, ");
            System.out.print("Choose your play: ");
            makeAMove(scannerInput.getUserInput().nextInt());
        }
    }

    @Override
    public String[][] makeAMove(int col) {
        if (col > gameBoard[0].length - 1 || col < 0) throw new IndexOutOfBoundsException();
        if (!gameBoard[5][col].equals(" ")) throw new ColumnFullException("This column is full");

        for (int i = 0; i < gameBoard.length; i++) {
            if (gameBoard[i][col].equals(" ")) {
                gameBoard[i][col] = playerOne ? "X" : "O";
                previousMove = "" + i + "" + col;
                playerOne = !playerOne;
                movesCounter++;
                break;
            }
        }
        printCurrentBoard();
        if (!checkWinner().equals("Draw")) {
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
        movesCounter = 0;
        gameBoard = new String[][]{
                {" ", " ", " ", " ", " ", " ", " "},
                {" ", " ", " ", " ", " ", " ", " "},
                {" ", " ", " ", " ", " ", " ", " "},
                {" ", " ", " ", " ", " ", " ", " "},
                {" ", " ", " ", " ", " ", " ", " "},
                {" ", " ", " ", " ", " ", " ", " "}};
    }

    public void printCurrentBoard() {
        bufferWriter.writeToFile(gameBoard, movesCounter, roundsCounter);
        System.out.println("\n┌───┬───┬───┬───┬───┬───┬───┐");
        for (int i = gameBoard.length - 1; i >= 0; i--) {
            System.out.print("│ ");
            for (int j = 0; j < gameBoard[i].length; j++) {
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
        int row = Integer.parseInt(previousMove.substring(0, 1));
        int col = Integer.parseInt(previousMove.substring(1));
        String currentPlayer = playerOne ? "O" : "X";
        int streak = 0;
        
        // Vertically down
        for (int i = 1; i < 4; i++) {
            if (row >= 3 && gameBoard[row - i][col].equals(currentPlayer)){
                streak++;
            }else{
                streak = 0;
            }
            if (streak == 3) {
                return currentPlayer.equals("X") ? "Player One" : "Player Two";
            }
        }

        //Horizontally total
        for (int i = 0; i < 7; i++) {
            if (gameBoard[row][i].equals(currentPlayer)) {
                streak++;
            } else {
                streak = 0;
            }
            if (streak == 4) {
                return currentPlayer.equals("X") ? "Player One" : "Player Two";
            }
        }

        //Diagonally from top left, get starting point
        int x = 0;
        int y = 0;
        for (int i = 1; i < 6; i++) {
            if (col != 0 && row != 5){
                if (col - i == 0 || row + i == 5) {
                    x = col - i;
                    y = row + i;
                    break;
                }
            }
            if (col == 0 || row == 5) {
                x = col;
                y = row;
            }
        }

        //Start checking from top left
        for (int i = 0; i < 7; i++) {
            if ((x+i) < 7 && (y-i) > -1) {
                if (gameBoard[y-i][x+i].equals(currentPlayer)) {
                    streak++;
                } else {
                    streak = 0;
                }
                if (streak == 4) {
                    return currentPlayer.equals("X") ? "Player One" : "Player Two";
                }
            }
        }

        //Diagonally from top right, get starting point
        x = 0;
        y = 0;
        for (int i = 1; i < 6; i++) {
            if (col != 6 && row != 5){
                if (col + i == 6 || row + i == 5) {
                    x = col + i;
                    y = row + i;
                    break;
                }
            }
            if (col == 6 || row == 5) {
                x = col;
                y = row;
            }
        }

        //Start checking from top right
        for (int i = 0; i < 7; i++) {
            if ((x-i) > -1 && (y-i) > -1) {
                if (gameBoard[y-i][x-i].equals(currentPlayer)) {
                    streak++;
                } else {
                    streak = 0;
                }
                if (streak == 4) {
                    return currentPlayer.equals("X") ? "Player One" : "Player Two";
                }
            }
        }
        return "Draw";
    }

    @Override
    public void viewReplay() {
        String[] previousRound = bufferReader.readFromFile();
        for (int i = 0; i < previousRound.length - 2; i++) {
            System.out.println(previousRound[i]);
        }
        System.out.println("Press ENTER to continue...");
    }

    @Override
    public String deleteReplay() {
        Path path = Paths.get("src/main/resources/Replay.txt");
        String result = Files.notExists(path) ? "There is no Replay to delete" : "Replay was deleted";
        try {
            Files.deleteIfExists(path);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        System.out.println(result.toUpperCase());
        System.out.println("Press ENTER to continue...");
        return result;
    }

    // Used by tests
    public void setPlayerOne(boolean playerOne) {
        this.playerOne = playerOne;
    }
}
