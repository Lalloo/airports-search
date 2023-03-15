package org.example.airportsearch.file.impl;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.example.airportsearch.file.FileRowsSearcher;
import org.example.airportsearch.file.data.Row;
import org.example.airportsearch.util.ComparatorUtil;

import java.util.*;

@FieldDefaults(level = AccessLevel.PRIVATE)
public class CSVFileRowsSearcher implements FileRowsSearcher {
    final Map<Character, List<Row>> columnValuesStartsWithMap;

    public CSVFileRowsSearcher(Map<Character, List<Row>> columnValuesStartsWithMap) {
        this.columnValuesStartsWithMap = columnValuesStartsWithMap;
    }

    @Override
    public List<Row> search(String prefix) {
        List<Row> result = new ArrayList<>();
        List<Row> outputList = columnValuesStartsWithMap.getOrDefault(Character.toLowerCase(prefix.charAt(0)), new ArrayList<>());

        Row prefixRow = new Row(prefix);
        Comparator<Row> rowComparator = ComparatorUtil.initializeComparatorForRows(prefix);

        int index = Collections.binarySearch(outputList, prefixRow, rowComparator);
        if (index < 0) {
            index = -index - 1;
        }

        while (index < outputList.size()) {
            Row row = outputList.get(index);
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