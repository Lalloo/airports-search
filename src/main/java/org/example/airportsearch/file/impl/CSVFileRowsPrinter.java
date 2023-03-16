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

@SuppressWarnings({"java:S3008", "java:S106"})
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class CSVFileRowsPrinter implements FileRowsPrinter {
    File file;

    public CSVFileRowsPrinter(File file) {
        this.file = file;
    }

    @Override
    public void print(List<Row> rows) {
        for (Row row : rows) {
            //Приходиться создавать новый ридер каждый раз из-за перемещения каретки
            //при реализации подобного перемещения через RandomAccessFile с использованием seek(long pos) ничего толкового не получилось
            try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                reader.skip(row.getSizeFromStartInCharacters());
                StringBuilder sb = new StringBuilder();
                for (int i = 0; i < row.getSizeInCharactersWithoutNewLine(); i++) {
                    sb.append((char) reader.read());
                }
                System.out.printf("%s[%s]%n", row.getColumnValue(), sb);
            } catch (IOException e) {
                System.err.println("Input-output exception " + e.getMessage());
            }
        }
    }
}
