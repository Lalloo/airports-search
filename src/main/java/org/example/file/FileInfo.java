package org.example.file;

import org.example.file.data.Row;

import java.util.List;

public interface FileInfo {

    void out(List<Row> result);

    List<Row> search(String prefix);
}
