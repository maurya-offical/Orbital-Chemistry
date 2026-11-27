package com.orbitalchemistry.exception;

public class EmailAlreadyUsedException extends RuntimeException {
    public EmailAlreadyUsedException(String email) {
        super("An account with this email already exists");
    }
}
