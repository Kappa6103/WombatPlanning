package com.wombatplanning.models.constraints;

import com.wombatplanning.models.entities.Client;

public class ClientChecker {

    public static void  requireValidClient(Client client) {
        if (client == null) {
            throw new IllegalArgumentException("Client cannot be null");
        }
    }

}
