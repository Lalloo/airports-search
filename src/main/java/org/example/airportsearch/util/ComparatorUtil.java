package org.example.airportsearch.util;

import org.example.airportsearch.file.data.Row;

import java.util.Comparator;

public class ComparatorUtil {

    private static final Comparator<Row> STRING_COMPARATOR = (r1, r2) -> String.CASE_INSENSITIVE_ORDER.compare(
            r1.getClearedColumnValue(),
            r2.getClearedColumnValue()
    );

    private static final Comparator<Row> DOUBLE_COMPARATOR = Comparator.comparingDouble(r -> Double.parseDouble(r.getClearedColumnValue()));

    private ComparatorUtil() {
        throw new UnsupportedOperationException("Utility class instantiation is not supported");
    }

    public static Comparator<Row> getForRows(String columnValue) {
        return isNumber(columnValue) ? DOUBLE_COMPARATOR : STRING_COMPARATOR;
    }

    private static boolean isNumber(String columnValue) {
        boolean isNumber = false;
        try {
            Double.parseDouble(columnValue);
            isNumber = true;
        } catch (NumberFormatException ignored) {
        }
        return isNumber;
    }
}
