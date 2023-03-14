package org.example.exception;

public class NumberOutOfRangeException extends IllegalArgumentException {
    public NumberOutOfRangeException(String message) {
        super(message);
    }
}
