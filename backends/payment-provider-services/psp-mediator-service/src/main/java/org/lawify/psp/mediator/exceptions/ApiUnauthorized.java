package org.lawify.psp.mediator.exceptions;

public class ApiUnauthorized extends RuntimeException {
    public ApiUnauthorized(String message) {
        super(message);
    }
}