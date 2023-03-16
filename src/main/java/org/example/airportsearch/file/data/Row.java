package org.example.airportsearch.file.data;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@NoArgsConstructor
@Setter
public class Row {
    private static final byte CHAR_SIZE_IN_BYTES = 2;

    private static final byte NEW_LINE_CHARACTER_IN_BYTES = 2;

    private static final byte NEW_LINE_CHARACTER = 1;

    private long bytesFromStart;

    private long sizeInBytes;

    private String columnValue;

    private String clearedColumnValue;

    public Row(String clearedColumnValue) {
        this.clearedColumnValue = clearedColumnValue;
    }

    public long getSizeFromStartInCharacters() {
        return bytesFromStart / CHAR_SIZE_IN_BYTES;
    }

    public long getSizeInCharactersWithoutNewLine() {
        return sizeInBytes / CHAR_SIZE_IN_BYTES - NEW_LINE_CHARACTER;
    }

    public void setLengthAsSizeInBytesPlusNewLineCharacter(long characterLength) {
        this.sizeInBytes = characterLength * CHAR_SIZE_IN_BYTES + NEW_LINE_CHARACTER_IN_BYTES;
    }
}