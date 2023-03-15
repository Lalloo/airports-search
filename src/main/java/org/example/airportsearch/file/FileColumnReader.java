package org.example.airportsearch.file;

import org.example.airportsearch.file.data.Row;

import java.io.File;
import java.util.List;
import java.util.Map;

public interface FileColumnReader {
    Map<Character, List<Row>> readColumnToMap(File file, int columnNumber);
}
