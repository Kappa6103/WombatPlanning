package com.wombatplanning.services.exceptions;

public class UserIdMisMatchException extends RuntimeException {
    public UserIdMisMatchException(String message) {
        super(message);
    }
}
