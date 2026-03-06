package com.wombatplanning.models.constraints;

import com.wombatplanning.models.entities.InterventionSchedule;

public class InterventionChecker {

    public static void requireValidIntervention(InterventionSchedule interventionSchedule) {
        if (interventionSchedule == null) {
            throw new IllegalArgumentException("Intervention cannot be null");
        }
    }
}
