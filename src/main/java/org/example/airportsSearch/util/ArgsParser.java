package org.example.airportsSearch.util;

import org.example.airportsSearch.exception.NumberOutOfRangeException;

public class ArgsParser {
    private ArgsParser() {
        throw new UnsupportedOperationException("Utility class instantiation is not supported ");
    }

    public static int parseArgs(String[] args) {
        if (args.length == 0) {
            throw new IllegalArgumentException("Column number is required as argument!");
        }
        if (args.length != 1) {
            throw new IllegalArgumentException("Need only one number");
        }
        int columnNumber;
        try {
            columnNumber = Integer.parseInt(args[0]);
            if (columnNumber < 1 || columnNumber > 14) {
                throw new NumberOutOfRangeException("Number of column must be in range 1-14");
            }
            return columnNumber;
        } catch (NumberFormatException e) {
            System.out.println("Not integer number");
        }
        throw new IllegalStateException("Unreachable State");
    }
}
