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

@FieldDefaults(level = AccessLevel.PRIVATE)
public class CSVFileColumnReader implements FileColumnReader {
    static final int CHAR_SIZE_IN_BYTES = 2;

    static final int SKIP_NEW_LINE_CHARACTER = 2;

    final Map<Character, List<Row>> columnValuesStartsWithMap = new HashMap<>();

    @Override
    public Map<Character, List<Row>> readColumnToMap(File file, int columnNumber) {
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line = reader.readLine();
            long bytesFromStart = 0;
            while (line != null) {
                Row newRow = new Row();
                int index = 1;
                for (String columnValue : line.split(",")) {
                    if (index == columnNumber) {
                        newRow.setColumnValue(columnValue);
                        newRow.setClearedColumnValue(columnValue.startsWith("\"") ? columnValue.substring(1, columnValue.length() - 1) : columnValue);

                        columnValuesStartsWithMap.computeIfAbsent(
                                Character.toLowerCase(newRow.getClearedColumnValue().charAt(0)),
                                k -> new ArrayList<>()
                        ).add(newRow);
                    }
                    index++;
                }

                newRow.setBytesFromStart(bytesFromStart);
                // Skip 2 bytes for '\n'
                newRow.setSizeInBytes(line.length() * CHAR_SIZE_IN_BYTES + SKIP_NEW_LINE_CHARACTER);

                bytesFromStart += newRow.getSizeInBytes();
                line = reader.readLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        sortMap();
        return columnValuesStartsWithMap;
    }

    private void sortMap() {
        Comparator<Row> rowComparator;
        for (List<Row> list : columnValuesStartsWithMap.values()) {
            rowComparator = ComparatorUtil.initializeComparatorForRows(list.get(0).getClearedColumnValue());
            list.sort(rowComparator);
        }
    }
}
