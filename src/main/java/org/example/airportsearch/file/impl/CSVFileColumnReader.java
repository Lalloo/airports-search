package org.example.airportsearch.file.impl;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.example.airportsearch.exception.NumberOutOfRangeException;
import org.example.airportsearch.file.FileColumnReader;
import org.example.airportsearch.file.data.Row;
import org.example.airportsearch.file.comparator.RowComparatorFactory;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

@SuppressWarnings("java:S106")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class CSVFileColumnReader implements FileColumnReader {
    Map<Character, List<Row>> columnMap = new HashMap<>();

    @Override
    public Map<Character, List<Row>> readColumnToMap(File file, int columnNumber) {
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line = reader.readLine();
            long bytesFromStart = 0;
            while (line != null) {
                Row row = new Row();
                int index = 1;
                String[] splitLine = line.split(",");
                if (splitLine.length < columnNumber) {
                    throw new NumberOutOfRangeException("Column not in row");
                }
                for (String columnValue : splitLine) {
                    if (index == columnNumber) {
                        row.setColumnValue(columnValue);
                        row.setClearedColumnValue(columnValue.startsWith("\"") ? columnValue.substring(1, columnValue.length() - 1) : columnValue);

                        columnMap.computeIfAbsent(
                                Character.toLowerCase(row.getClearedColumnValue().charAt(0)),
                                k -> new ArrayList<>()
                        ).add(row);
                    }
                    index++;
                }
                row.setBytesFromStart(bytesFromStart);
                row.setLengthAsSizeInBytesPlusNewLineCharacter(line.length());
                bytesFromStart += row.getSizeInBytes();
                line = reader.readLine();
            }
        } catch (IOException e) {
            System.err.printf("Input-output exception %s", e.getMessage());
            System.exit(1);
        }
        Comparator<Row> rowComparator;
        for (List<Row> list : columnMap.values()) {
            rowComparator = RowComparatorFactory.getComparator(list.get(0).getClearedColumnValue());
            list.sort(rowComparator);
        }
        return columnMap;
    }
}
