package org.example.airportsearch.util;

import org.example.airportsearch.file.data.Row;

import java.util.Comparator;
import java.util.concurrent.atomic.AtomicBoolean;

public class ComparatorUtil {
    private ComparatorUtil() {
        throw new UnsupportedOperationException("Utility class instantiation is not supported");
    }

    public static Comparator<Row> initializeComparatorForRows(String columnValue) {
        return (r1, r2) -> {
            if (isNumber(columnValue).get()) {
                return Double.compare(
                        Double.parseDouble(r1.getClearedColumnValue()),
                        Double.parseDouble(r2.getClearedColumnValue())
                );
            } else {
                return String.CASE_INSENSITIVE_ORDER.compare(
                        r1.getClearedColumnValue(),
                        r2.getClearedColumnValue()
                );
            }
        };
    }

    private static AtomicBoolean isNumber(String columnValue) {
        AtomicBoolean isNumber = new AtomicBoolean(false);
        try {
            Double.parseDouble(columnValue);
            isNumber.set(true);
        } catch (NumberFormatException ignored) {
        }
        return isNumber;
    }
}
