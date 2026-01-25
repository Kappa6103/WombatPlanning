package com.wombatplanning.models.entities;

public class UserFactory {

    private static final String name = "name";
    private static final String email = "email";
    private static final String password = "password";

    public static User createUser() {
        User user = User.create(
                name,
                email,
                password);
        return user;
    }
}
