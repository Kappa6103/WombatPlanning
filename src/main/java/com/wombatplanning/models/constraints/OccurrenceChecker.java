package com.wombatplanning.models.constraints;

public class OccurrenceChecker {

    public static void requireValidOccurence(Integer occurrence) {
        if (occurrence == null) {
            throw new IllegalArgumentException("occurrence cannot be null");
        }
        if (ColumnConstraints.OCCURRENCE_MIN > occurrence || occurrence > ColumnConstraints.OCCURRENCE_MAX) {
            throw new IllegalArgumentException(
                    String.format("Occurrence must be between %d and %d",
                            ColumnConstraints.OCCURRENCE_MIN,
                            ColumnConstraints.OCCURRENCE_MAX));
        }


    }

}
