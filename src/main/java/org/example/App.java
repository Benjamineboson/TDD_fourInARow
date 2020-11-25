package org.example;


import org.example.game_engine.GameBoard;

import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.List;

/**
 * Hello world!
 *
 */
public class App 
{

    public static void main( String[] args )
    {
        GameBoard gameBoard = new GameBoard();
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

        String anotherBoardStyle =  "╔═══╦═══╦═══╦═══╦═══╦═══╦═══╗\n" +
                                    "║   ║ X ║   ║   ║   ║   ║   ║\n" +
                                    "╠═══╬═══╬═══╬═══╬═══╬═══╬═══╣\n" +
                                    "║   ║ X ║ O ║   ║   ║   ║   ║\n" +
                                    "╚═══╩═══╩═══╩═══╩═══╩═══╩═══╝\n";
        System.out.println(anotherBoardStyle);
    }
}
