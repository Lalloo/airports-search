package org.example.airportsearch.file;

import org.example.airportsearch.file.data.Row;

import java.util.List;
import java.util.Map;

public interface FileRowsSearcher {

    List<Row> search(String prefix);
}
