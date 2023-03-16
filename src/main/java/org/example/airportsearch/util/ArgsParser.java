package org.example.airportsearch.util;

@SuppressWarnings("java:S106")
public class ArgsParser {

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
            return columnNumber;
        } catch (NumberFormatException e) {
            System.err.println("Not integer number!");
            System.exit(1);
        }
        throw new IllegalStateException("Unreachable State");
    }
}
