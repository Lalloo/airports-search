package org.example.airportsearch.file.impl;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.example.airportsearch.file.FileColumnReader;
import org.example.airportsearch.file.data.Row;
import org.example.airportsearch.util.ComparatorUtil;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

@SuppressWarnings("java:S106")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class CSVFileColumnReader implements FileColumnReader {
    Map<Character, List<Row>> columnValuesStartsWithMap = new HashMap<>();

    @Override
    public Map<Character, List<Row>> readColumnToMap(File file, int columnNumber) {
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line = reader.readLine();
            long bytesFromStart = 0;
            while (line != null) {
                Row row = new Row();
                int index = 1;
                for (String columnValue : line.split(",")) {
                    if (index == columnNumber) {
                        row.setColumnValue(columnValue);
                        row.setClearedColumnValue(columnValue.startsWith("\"") ? columnValue.substring(1, columnValue.length() - 1) : columnValue);

                        columnValuesStartsWithMap.computeIfAbsent(
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
            System.err.println("Input-output exception " + e.getMessage());
        }
        sortMap();
        return columnValuesStartsWithMap;
    }

    private void sortMap() {
        Comparator<Row> rowComparator;
        for (List<Row> list : columnValuesStartsWithMap.values()) {
            rowComparator = ComparatorUtil.getForRows(list.get(0).getClearedColumnValue());
            list.sort(rowComparator);
        }
    }
}
