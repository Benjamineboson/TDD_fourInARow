package org.example.IO_utils;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class BWriter {

    String directory = "src/main/resources";
    String fileName = "fileName.txt";
    String absolutePath = directory + File.separator + fileName;

    public void writeToFile(String[][] gameBoard) {

        try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(absolutePath))) {

            for (int i = gameBoard.length - 1; i >= 0; i--) {
                for (int j = 0; j < gameBoard[i].length ; j++) {
                    bufferedWriter.write("┌───┬───┬───┬───┬───┬───┬───┐");
                    bufferedWriter.write("│ " + gameBoard[i][j] + " │ ");
                    if (i >= 1) {
                        bufferedWriter.write("├───┼───┼───┼───┼───┼───┼───┤");
                    } else {
                        bufferedWriter.write("└───┴───┴───┴───┴───┴───┴───┘");
                    }
                }
            }

        }catch (IOException e) {
            System.out.print(e.getMessage());
        }
    }

}
