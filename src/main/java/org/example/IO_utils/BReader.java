package org.example.IO_utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class BReader {
    String directory = "src/main/resources";
    String history = "";

    public String[] readFromFile() {
        String fileName =  "fileName.txt";
        String absolutePath = directory + File.separator + fileName;
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(absolutePath))) {
            String line = "";
            while(line != null) {
                line = bufferedReader.readLine();
                history += line+"\n";
            }
        } catch (IOException e) {
            e.getMessage();
        }
        String[] historyArr = history.split(",");
        return historyArr;
    }
}
