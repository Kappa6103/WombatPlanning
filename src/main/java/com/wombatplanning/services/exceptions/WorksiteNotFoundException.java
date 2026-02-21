package com.wombatplanning.services.exceptions;

public class WorksiteNotFoundException extends RuntimeException {
    public WorksiteNotFoundException(String message) {
        super(message);
    }
}
