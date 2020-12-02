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
    int playerOneDiagonallyToTheRight = 0;
    int playerTwoDiagonallyToTheRight = 0;
    int playerOneDiagonallyToTheLeft = 0;
    int playerTwoDiagonallyToTheLeft = 0;

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
        resetBoard();
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
            if (row >= 3 && gameBoard[row - i][col].equals(currentPlayer)) streak++;
            if (streak == 3) {
                return currentPlayer.equals("X") ? "Player One" : "Player Two";
            }
        }
        streak = 0;
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

        streak = 0;
        //Diagonally down and to the left
        for (int i = 1; i < 4; i++) {
            if ((row >= 3 && col >= 3) && gameBoard[row - i][col - i].equals(currentPlayer)){
                if (currentPlayer.equals("X")) playerOneDiagonallyToTheRight++;
                if (currentPlayer.equals("O")) playerTwoDiagonallyToTheRight++;
            }
            if (playerOneDiagonallyToTheRight == 3) return "Player One";
            if (playerTwoDiagonallyToTheRight == 3) return "Player Two";
        }
//        streak = 0;
        //Diagonally up and to the right
        for (int i = 1; i < 4; i++) {
            if ((row < 3 && col <= 3) && gameBoard[row + i][col + i].equals(currentPlayer)){
                if (currentPlayer.equals("X")) playerOneDiagonallyToTheRight++;
                if (currentPlayer.equals("O")) playerTwoDiagonallyToTheRight++;
            }
            if (playerOneDiagonallyToTheRight == 3) return "Player One";
            if (playerTwoDiagonallyToTheRight == 3) return "Player Two";
        }
        //Diagonally up and to the left
        for (int i = 1; i < 4; i++) {
            if ((row < 3 && col >= 3) && gameBoard[row + i][col - i].equals(currentPlayer)){
                if (currentPlayer.equals("X")) playerOneDiagonallyToTheLeft++;
                if (currentPlayer.equals("O")) playerTwoDiagonallyToTheLeft++;
            }
            if (playerOneDiagonallyToTheLeft == 3) return "Player One";
            if (playerTwoDiagonallyToTheLeft == 3) return "Player Two";
        }
        //Diagonally down and to the right
        for (int i = 1; i < 4; i++) {
            if ((row >= 3 && col <= 3) && (gameBoard[row - i][col + i].equals(currentPlayer))){
                if (currentPlayer.equals("X")) playerOneDiagonallyToTheLeft++;
                if (currentPlayer.equals("O")) playerTwoDiagonallyToTheLeft++;
            }
            if (playerOneDiagonallyToTheLeft == 3) return "Player One";
            if (playerTwoDiagonallyToTheLeft == 3) return "Player Two";
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
        Path path = Paths.get("src/main/resources/fileName.txt");
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
