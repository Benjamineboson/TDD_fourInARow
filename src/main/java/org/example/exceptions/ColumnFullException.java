package org.example.exceptions;

public class ColumnFullException extends RuntimeException {
    public ColumnFullException(String message) {
        super(message);
    }
}
