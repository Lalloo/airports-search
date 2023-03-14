package org.example;

import org.example.file.data.Row;
import org.example.file.impl.CSVFileInfo;
import org.example.util.ArgsParser;

import java.io.*;
import java.util.List;
import java.util.Scanner;

public class AirportSearchApplication {
    private static final Scanner SCANNER = new Scanner(System.in);
    private static final String PATH_TO_FILE = "src/main/resources/airports.csv";

    public static void main(String[] args) {
        int column = ArgsParser.parseArgs(args);
        CSVFileInfo fileInfo = new CSVFileInfo(new File(PATH_TO_FILE), column);
        while (true) {
            System.out.print("input: ");
            String input = SCANNER.nextLine();
            if (input.equals("!quit")) {
                break;
            }
            long startTime = System.currentTimeMillis();
            List<Row> result = fileInfo.search(input);
            long endTime = System.currentTimeMillis();
            fileInfo.out(result);
            System.out.println("Количество найденных строк: " + result.size() + ". Затраченное на поиск время: " + (endTime - startTime) + "ms");
        }
    }
}