package org.example.IO_utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

public class BReader {
    String directory = "src/main/resources";
    List<String> lines = null;

    public List<String> readFromFile() {
        String fileName =  "fileName.txt";
        String absolutePath = directory + File.separator + fileName;

        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(absolutePath))) {
            String line = bufferedReader.readLine();
            while(line != null) {
                line = bufferedReader.readLine();
                lines.add(line);
            }
        } catch (IOException e) {
            e.getMessage();
        }

        return lines;
    }
}
