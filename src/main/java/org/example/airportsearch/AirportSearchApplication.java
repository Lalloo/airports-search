package org.example.airportsearch;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.example.airportsearch.file.FileColumnReader;
import org.example.airportsearch.file.FileRowsSearcher;
import org.example.airportsearch.file.FileRowsPrinter;
import org.example.airportsearch.file.data.Row;
import org.example.airportsearch.file.impl.CSVFileColumnReader;
import org.example.airportsearch.file.impl.CSVFileRowsSearcher;
import org.example.airportsearch.file.impl.CSVFileRowsPrinter;
import org.example.airportsearch.util.ArgsParser;

import java.io.*;
import java.util.List;
import java.util.Scanner;

@SuppressWarnings("java:S106")
public class AirportSearchApplication {
    private static final Scanner SCANNER = new Scanner(System.in);
    private static final String PATH_TO_FILE = "src/main/resources/airports.csv";

    public static void main(String[] args) {
        int column = ArgsParser.parseArgs(args);
        File airportsCSV = new File(PATH_TO_FILE);
        FileColumnReader fileColumnReader = new CSVFileColumnReader();
        FileRowsSearcher fileRowsSearcher = new CSVFileRowsSearcher(fileColumnReader.readColumnToMap(airportsCSV, column));
        FileRowsPrinter fileRowsPrinter = new CSVFileRowsPrinter(airportsCSV);
        while (true) {
            System.out.print("input: ");
            String input = SCANNER.nextLine();
            if (input.equals("!quit")) {
                break;
            }
            long startTime = System.currentTimeMillis();
            List<Row> result = fileRowsSearcher.search(input);
            long finishTime = System.currentTimeMillis();
            fileRowsPrinter.print(result);
            System.out.println("Количество найденных строк: " + result.size() + ". Затраченное на поиск время: " + (finishTime - startTime) + "ms");
        }
    }
}