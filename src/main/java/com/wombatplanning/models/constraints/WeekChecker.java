package com.wombatplanning.models.constraints;

import com.wombatplanning.models.entities.Week;

public class WeekChecker {

    public static void  requireValidWeek(Week week) {
        if (week == null) {
            throw new IllegalArgumentException("Week cannot be null");
        }
    }
}
