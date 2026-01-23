package com.wombatplanning.models.constraints;


import com.wombatplanning.models.entities.Worksite;

public class WorksiteChecker {

    public static void requireValidWorksite(Worksite worksite) {
        if (worksite == null) {
            throw new IllegalArgumentException("Worksite cannot be null");
        }
    }

}
