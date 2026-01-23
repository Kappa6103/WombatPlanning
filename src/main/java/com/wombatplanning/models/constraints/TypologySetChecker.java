package com.wombatplanning.models.constraints;

import com.wombatplanning.models.entities.Typology;

import java.util.Set;

public class TypologySetChecker {

    public static void requireValidTypologySet(Set<Typology> typologySet) {
        if (typologySet == null) {
            throw new IllegalArgumentException("Typology set cannot be null");
        }
        if (typologySet.isEmpty()) {
            throw new IllegalArgumentException("Typology set cannot be empty");
        }
    }
}
