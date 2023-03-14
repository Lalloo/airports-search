package org.example.airportsSearch.file;

import org.example.airportsSearch.file.data.Row;

import java.util.List;

public interface FileInfo {

    void out(List<Row> result);

    List<Row> search(String prefix);
}
