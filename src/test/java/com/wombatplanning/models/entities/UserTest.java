package com.wombatplanning.models.entities;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UserTest {

    private final String name = "name";
    private final String email = "email";
    private final String password = "password";

    @Test
    void create_user_is_admin_false_by_default() {
        User user = User.create(
                name,
                email,
                password);
        assertNull(user.getId());
        assertEquals(user.getName(), name);
        assertEquals(user.getEmail(), email);
        assertEquals(user.getPassword(), password);
        assertFalse(user.isAdmin());
        assertTrue(user.getClientSet().isEmpty());
        assertTrue(user.getWorksiteSet().isEmpty());
        assertTrue(user.getInterventionsSet().isEmpty());
        assertTrue(user.getScheduledTaskSet().isEmpty());
        assertTrue(user.getWeekSet().isEmpty());
        assertTrue(user.getTypologySet().isEmpty());
    }

    @Test
    void create_user_with_factory_helper() {
        User user = UserFactory.createUser();
        assertNull(user.getId());
        assertEquals(user.getName(), name);
        assertEquals(user.getEmail(), email);
        assertEquals(user.getPassword(), password);
        assertFalse(user.isAdmin());
        assertTrue(user.getClientSet().isEmpty());
        assertTrue(user.getWorksiteSet().isEmpty());
        assertTrue(user.getInterventionsSet().isEmpty());
        assertTrue(user.getScheduledTaskSet().isEmpty());
        assertTrue(user.getWeekSet().isEmpty());
        assertTrue(user.getTypologySet().isEmpty());
    }
}