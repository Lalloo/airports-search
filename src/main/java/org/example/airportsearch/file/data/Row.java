package org.example.airportsearch.file.data;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@Getter
@NoArgsConstructor
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Row {
    long bytesFromStart;
    int sizeInBytes;
    String columnValue;
    String clearedColumnValue;

    public Row(String clearedColumnValue) {
        this.clearedColumnValue = clearedColumnValue;
    }
}