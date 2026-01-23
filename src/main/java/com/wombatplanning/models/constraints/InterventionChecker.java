package com.wombatplanning.models.constraints;

import com.wombatplanning.models.entities.Intervention;

public class InterventionChecker {

    public static void requireValidIntervention(Intervention intervention) {
        if (intervention == null) {
            throw new IllegalArgumentException("Intervention cannot be null");
        }
    }
}
