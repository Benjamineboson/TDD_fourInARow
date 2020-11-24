package org.example;

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
        List<String> winningMoves = new ArrayList<>();
        String [][] arr = new String[6][7];

        for (int i = 0; i < arr.length ; i++) {
            for (int j = 0; j < arr[i].length ; j++) {
                arr[i][j] = ""+i+":"+j;
                winningMoves.add(arr[i][j]);
            }
        }

        for (int i = arr.length-1; i >= 0; i--) {
            System.out.print("| ");
            for (int j = 0; j < arr[i].length ; j++) {
                System.out.print(arr[i][j] + " | ");
            }
            System.out.println();
            System.out.println("-------------------------------------------");
        }
    }

}
