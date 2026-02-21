package com.wombatplanning.models.constraints;

import com.wombatplanning.models.entities.Week;

public class WeekChecker {

    public static void  requireValidWeek(Week week) {
        if (week == null) {
            throw new IllegalArgumentException("Week cannot be null");
        }
    }

    public static void requireValidStartingWeek(Integer startingWeek) {
        if (startingWeek == null) {
            throw new IllegalArgumentException("Starting week cannot be null");
        }
        if (ColumnConstraints.WEEK_MIN > startingWeek || startingWeek > ColumnConstraints.WEEK_MAX) {
            throw new IllegalArgumentException(
                    String.format("Starting week must be between %d and %d",
                            ColumnConstraints.WEEK_MIN,
                            ColumnConstraints.WEEK_MAX));
        }
    }

    public static void requireValidEndingWeek(Integer endingWeek) {
        if (endingWeek == null) {
            throw new IllegalArgumentException("Ending week cannot be null");
        }
        if (ColumnConstraints.WEEK_MIN > endingWeek || endingWeek > ColumnConstraints.WEEK_MAX) {
            throw new IllegalArgumentException(
                    String.format("Ending week must be between %d and %d",
                            ColumnConstraints.WEEK_MIN,
                            ColumnConstraints.WEEK_MAX));
        }
    }
}
