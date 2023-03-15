package org.example.airportsearch.file;

import org.example.airportsearch.file.data.Row;

import java.util.List;

public interface FileRowsPrinter {
    void print(List<Row> rowsToPrint);
}
