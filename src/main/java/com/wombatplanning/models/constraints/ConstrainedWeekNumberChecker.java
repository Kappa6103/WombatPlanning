package com.wombatplanning.models.constraints;

public class ConstrainedWeekNumberChecker {

    public static void requireValidWeekNumber(Integer week) {
        if (week == null) {
            throw new IllegalArgumentException("Week cannot be null");
        }
        if (ColumnConstraints.WEEK_BEGINNING > week || week > ColumnConstraints.WEEK_ENDING) {
            throw new IllegalArgumentException(
                    String.format(
                            "Week must be between %d and %d",
                            ColumnConstraints.WEEK_BEGINNING,
                            ColumnConstraints.WEEK_ENDING));
        }
    }
}
