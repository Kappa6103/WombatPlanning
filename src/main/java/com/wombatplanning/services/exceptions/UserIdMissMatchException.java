package com.wombatplanning.services.exceptions;

public class UserIdMissMatchException extends RuntimeException {
    public UserIdMissMatchException(String message) {
        super(message);
    }
}
