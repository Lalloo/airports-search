package org.example.airportsearch.util;

import org.example.airportsearch.exception.NumberOutOfRangeException;

@SuppressWarnings("java:S106")
public class ArgsParser {
    private static final int FIRST_COLUMN = 1;

    private static final int LAST_COLUMN = 14;

    private ArgsParser() {
        throw new UnsupportedOperationException("Utility class instantiation is not supported");
    }

    public static int parseArgs(String[] args) {
        if (args.length == 0) {
            throw new IllegalArgumentException("Column number is required as argument!");
        }
        if (args.length != 1) {
            throw new IllegalArgumentException("Need only one number in range 1-14!");
        }
        int columnNumber;
        try {
            columnNumber = Integer.parseInt(args[0]);
            if (columnNumber < FIRST_COLUMN || columnNumber > LAST_COLUMN) {
                throw new NumberOutOfRangeException("Number of column must be in range 1-14!");
            }
            return columnNumber;
        } catch (NumberFormatException e) {
            System.err.println("Not integer number!");
        }
        throw new IllegalStateException("Unreachable State");
    }
}
