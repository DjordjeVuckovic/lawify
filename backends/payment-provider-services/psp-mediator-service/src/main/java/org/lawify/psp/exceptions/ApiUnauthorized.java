package org.lawify.psp.exceptions;

public class ApiUnauthorized extends RuntimeException {
    public ApiUnauthorized(String message) {
        super(message);
    }
}