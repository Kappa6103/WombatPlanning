package com.wombatplanning.models.constraints;

public class YearChecker {

    public static void requireValidYear(Integer year) {
        if (year == null) {
            throw new IllegalArgumentException("Year cannot be null");
        }
        if (ColumnConstraints.YEAR_BEGINNING > year || year > ColumnConstraints.YEAR_ENDING) {
            throw new IllegalArgumentException(
                    String.format(
                            "Year must be between %d and %d",
                            ColumnConstraints.YEAR_BEGINNING,
                            ColumnConstraints.YEAR_ENDING));
        }
    }
}
