package org.example;

import org.example.IO_utils.ScannerInput;
import org.example.game_engine.GameBoard;

import java.util.Scanner;

/**
 * Hello world!
 *
 */
public class App 
{
    static ScannerInput scannerInput = new ScannerInput();
    static GameBoard gameBoard = new GameBoard();
    static boolean keepRun = true;


    public static void main( String[] args )
    {
            printMenu();
            while (keepRun) {
                String selection = scannerInput.getUserInput().nextLine();
                switch (selection) {
                    case "1":
                        gameBoard.play();
                        break;
                    case "2":
                        printBoard(); // will be changed later...
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
        System.out.println("2. Replay a game");
        System.out.println("Q. Exit to desktop");
    }

    // temporary method.....
    public static void printBoard() {
            String [][] arr = new String[6][7];
            for (int i = 0; i < 7; i++) {
                if (i%2 == 0) arr = gameBoard.makeAMove(0);
                else arr = gameBoard.makeAMove(1);
            }
            System.out.println(gameBoard.checkWinner());
            for (int i = arr.length-1; i >= 0; i--) {
                System.out.print("| ");
                for (int j = 0; j < arr[i].length ; j++) {
                    System.out.print(arr[i][j] + " | ");
                }
                System.out.println();
                System.out.println("-----------------------------");
            }

            System.out.println("┌───┬───┬───┬───┬───┬───┬───┐");
            for (int i = arr.length-1; i >= 0; i--) {
                System.out.print("│ ");
                for (int j = 0; j < arr[i].length ; j++) {
                    System.out.print(arr[i][j] + " │ ");
                }
                System.out.println();
                if (i >= 1) {
                    System.out.println("├───┼───┼───┼───┼───┼───┼───┤");
                } else {
                    System.out.println("└───┴───┴───┴───┴───┴───┴───┘");
                }
            }

            String anotherBoardStyle =
                    "╔═══╦═══╦═══╦═══╦═══╦═══╦═══╗\n" +
                            "║   ║ X ║   ║   ║   ║   ║   ║\n" +
                            "╠═══╬═══╬═══╬═══╬═══╬═══╬═══╣\n" +
                            "║   ║ X ║ O ║   ║   ║   ║   ║\n" +
                            "╚═══╩═══╩═══╩═══╩═══╩═══╩═══╝\n";
            System.out.println(anotherBoardStyle);
        }
}
