package org.example.airportsearch.file.impl;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.example.airportsearch.file.FileRowsPrinter;
import org.example.airportsearch.file.data.Row;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@SuppressWarnings({"java:S3008","java:S106"})
public class CSVFileRowsPrinter implements FileRowsPrinter {
    File file;
    static int CHAR_SIZE_IN_BYTES = 2;

    public CSVFileRowsPrinter(File file) {
        this.file = file;
    }

    @Override
    public void print(List<Row> rowsToPrint) {
        for (Row row : rowsToPrint) {
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
