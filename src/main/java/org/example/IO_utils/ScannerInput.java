package org.example.IO_utils;

import java.util.Scanner;

public class ScannerInput {
    Scanner scannerInput;

    public ScannerInput() {
        this.scannerInput = new Scanner(System.in);
    }

    public Scanner getUserInput() {
        return scannerInput;
    }
}
