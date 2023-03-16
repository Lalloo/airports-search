package org.example.airportsearch.file.impl;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.example.airportsearch.file.FileRowsSearcher;
import org.example.airportsearch.file.data.Row;
import org.example.airportsearch.file.comparator.RowComparatorFactory;

import java.util.*;

@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class CSVFileRowsSearcher implements FileRowsSearcher {
    Map<Character, List<Row>> columnValuesStartsWithMap;

    public CSVFileRowsSearcher(Map<Character, List<Row>> columnValuesStartsWithMap) {
        this.columnValuesStartsWithMap = columnValuesStartsWithMap;
    }

    @Override
    public List<Row> search(String prefix) {
        List<Row> result = new ArrayList<>();
        List<Row> rows = columnValuesStartsWithMap.getOrDefault(Character.toLowerCase(prefix.charAt(0)), new ArrayList<>());
        Row prefixRow = new Row(prefix);
        Comparator<Row> rowComparator = RowComparatorFactory.getComparator(prefix);
        int index = Collections.binarySearch(rows, prefixRow, rowComparator);
        if (index < 0) {
            index = -index - 1;
        }
        while (index < rows.size()) {
            Row row = rows.get(index);
            String value = row.getClearedColumnValue();
            if (!value.startsWith(prefix)) {
                break;
            }
            result.add(row);
            index++;
        }
        return result;
    }
}