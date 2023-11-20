package org.lawify.psp.mediator.shared.exceptions;

public class ApiUnauthorized extends RuntimeException {
    public ApiUnauthorized(String message) {
        super(message);
    }
}