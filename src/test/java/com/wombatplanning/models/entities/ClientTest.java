package com.wombatplanning.models.entities;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ClientTest {

    private final String clientName = "client name";

    @Test
    void createClient() {
        User user = UserFactory.createUser();
        Client client1 = Client.create(user, clientName);
        Client client2 = Client.create(user, clientName);

        assertTrue(user.getClientSet().contains(client1));
        assertEquals("name", client1.getUserName());
        assertEquals(clientName, client1.getName());

    }

}