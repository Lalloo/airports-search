package org.example.airportsearch.file;

import org.example.airportsearch.file.data.Row;

import java.util.List;

public interface FileRowsSearcher {
    List<Row> search(String prefix);
}
