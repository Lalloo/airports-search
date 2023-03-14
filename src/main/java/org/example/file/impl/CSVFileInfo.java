package org.example.file.impl;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.example.file.FileInfo;
import org.example.file.data.Row;

import java.io.*;
import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;

@FieldDefaults(level = AccessLevel.PRIVATE)
public class CSVFileInfo implements FileInfo {
    final File file;
    final Map<Character, List<Row>> columnValuesStartsWithMap = new TreeMap<>();
    Comparator<Row> comparatorForRows;

    static final int CHAR_SIZE_IN_BYTES = 2;

    static final int SKIP_NEW_LINE_CHARACTER = 2;

    public CSVFileInfo(File file, int columnNumber) {
        this.file = file;
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line = reader.readLine();
            int rowNumber = 0;
            long bytesFromStart = 0;
            while (line != null) {
                Row newRow = new Row();
                newRow.setNumber(rowNumber);
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
                rowNumber++;
                line = reader.readLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        //todo добавить флажок на инициализацию число или нет получается, чтобы не инициализировать компаратор заново на строках идущих подряд например
        for (List<Row> list : columnValuesStartsWithMap.values()) {
            comparatorForRows = initializeComparatorForRows(list.get(0).getClearedColumnValue());
            list.sort(comparatorForRows);
        }
    }

    private Comparator<Row> initializeComparatorForRows(String columnValue) {
        return (r1, r2) -> {
            if (isNumber(columnValue).get()) {
                return Double.compare(
                        Double.parseDouble(r1.getClearedColumnValue()),
                        Double.parseDouble(r2.getClearedColumnValue())
                );
            } else {
                return String.CASE_INSENSITIVE_ORDER.compare(
                        r1.getClearedColumnValue(),
                        r2.getClearedColumnValue()
                );
            }
        };
    }

    private AtomicBoolean isNumber(String columnValue) {
        AtomicBoolean isNumber = new AtomicBoolean(false);
        try {
            Double.parseDouble(columnValue);
            isNumber.set(true);
        } catch (NumberFormatException ignored) {
        }
        return isNumber;
    }

    @Override
    public List<Row> search(String prefix) {
        List<Row> result = new ArrayList<>();
        boolean findFirstResult = false;
        List<Row> outputSet = columnValuesStartsWithMap.getOrDefault(Character.toLowerCase(prefix.charAt(0)), new ArrayList<>());
        for (Row row : outputSet) {
            boolean startsWith = row.getClearedColumnValue().startsWith(prefix);
            if (startsWith) {
                result.add(row);
                findFirstResult = true;
            }
            if (findFirstResult && !startsWith) {
                break;
            }
        }
        return result;
        //  worse
        //        return columnValuesStartsWithMap.get(Character.toLowerCase(prefix.charAt(0))).stream()
        //                .filter(row -> row.getClearedColumnValue().startsWith(prefix))
        //                .collect(Collectors.toList());
    }

    @Override
    public void out(List<Row> result) {
        for (Row row : result) {
            //Приходиться создавать новый ридер каждый раз из-за перемещения каретки, подобное реализовать с чем-то другим не получилось
            try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                reader.skip((row.getBytesFromStart() / CHAR_SIZE_IN_BYTES));
                StringBuilder sb = new StringBuilder();
                for (int i = 0; i < row.getSizeInBytes() / CHAR_SIZE_IN_BYTES - 1; i++) {
                    sb.append((char) reader.read());
                }
                System.out.println(row.getColumnValue() + "[" + sb + "]");
            } catch (IOException exception) {
                exception.printStackTrace();
            }
        }
    }
}