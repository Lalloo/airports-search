package org.example.airportsearch.file.comparator;

import org.example.airportsearch.file.data.Row;

import java.util.Comparator;

public final class RowComparatorFactory {

    private static final Comparator<Row> STRING_COMPARATOR = (r1, r2) -> String.CASE_INSENSITIVE_ORDER.compare(
            r1.getClearedColumnValue(),
            r2.getClearedColumnValue()
    );

    private static final Comparator<Row> DOUBLE_COMPARATOR = Comparator.comparingDouble(r -> Double.parseDouble(r.getClearedColumnValue()));

    private RowComparatorFactory() {
        throw new UnsupportedOperationException("Utility class instantiation is not supported");
    }

    public static Comparator<Row> getComparator(String columnValue) {
        return isNumber(columnValue) ? DOUBLE_COMPARATOR : STRING_COMPARATOR;
    }

    private static boolean isNumber(String columnValue) {
        try {
            Double.parseDouble(columnValue);
            return true;
        } catch (NumberFormatException ignored) {
            return false;
        }
    }
}
