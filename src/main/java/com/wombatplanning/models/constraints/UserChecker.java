package com.wombatplanning.models.constraints;

import com.wombatplanning.models.entities.User;

public class UserChecker {

    public static void  requireValidUser(User user) {
        if (user == null) {
            throw new IllegalArgumentException("User cannot be null");
        }
    }
}
