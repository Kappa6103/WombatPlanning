package com.wombatplanning.services.exceptions;

public class DuplicateClientException extends RuntimeException {

    public DuplicateClientException(String message) {
        super(message);
    }

}
