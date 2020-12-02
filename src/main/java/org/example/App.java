package org.example;

import org.example.IO_utils.ScannerInput;
import org.example.game_engine.GameBoard;

public class App {
    static ScannerInput scannerInput = new ScannerInput();
    static GameBoard gameBoard = new GameBoard();
    static boolean keepRun = true;

    public static void main( String[] args ) {
        startApp();
    }

    public static void startApp() {
        printMenu();
        while (keepRun) {
            String selection = scannerInput.getUserInput().nextLine();
            switch (selection) {
                case "1":
                    gameBoard.setNumberOfRounds();
                    break;
                case "2":
                    gameBoard.viewReplay();
                    break;
                case "3":
                    gameBoard.deleteReplay();
                    break;
                case "Q": case "q":
                    System.out.println("Quit...");
                    scannerInput.getUserInput().close();
                    keepRun = false;
                    break;
                default:
                    printMenu();
            }
        }
    }

    private static void printMenu() {
        System.out.println("Connect four - the Game ");
        System.out.println("\nMain Menu:");
        System.out.println("1. Play a game of connect four");
        System.out.println("2. View Replay of latest games");
        System.out.println("3. Delete Replay of latest games");
        System.out.println("Q. Exit to desktop");
    }
}
