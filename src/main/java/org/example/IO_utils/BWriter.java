package org.example.IO_utils;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class BWriter {

    String directory = "src/main/resources";
    String fileName = "fileName.txt";
    String absolutePath = directory + File.separator + fileName;

    public void writeToFile(String[][] gameBoard,int currentMove, int currentRound) {
        try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(absolutePath,true))) {
            StringBuilder builder = new StringBuilder();
            if (currentRound == 0) builder.append("----- Created From Test -----\n");
            builder.append("Round: "+currentRound+" Move: "+currentMove+"\n");
            builder.append(("┌───┬───┬───┬───┬───┬───┬───┐\n"));
            for (int i = gameBoard.length-1; i >= 0 ; i--) {
                for (int j = 0; j < gameBoard[i].length ; j++) {
                    if (j == gameBoard[i].length -1){
                        builder.append("| "+gameBoard[i][j]+" |\n");
                        if (i > 0) builder.append("├───┼───┼───┼───┼───┼───┼───┤\n");
                        if (i == 0) builder.append("└───┴───┴───┴───┴───┴───┴───┘,");
                    }else{
                        builder.append("| "+gameBoard[i][j]+" ");
                    }
                }
            }
            bufferedWriter.append(builder.toString()+"\n");
        }catch (IOException e) {
            System.out.print(e.getMessage());
        }
    }

}
