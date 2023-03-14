package org.example.airportsSearch.file.data;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Row {
    int number;
    long bytesFromStart;
    int sizeInBytes;
    String columnValue;
    String clearedColumnValue;
}