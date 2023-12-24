package org.lawify.psp.crypto.shared.exceptions;

public class ApiUnauthorized extends RuntimeException {
    public ApiUnauthorized(String message) {
        super(message);
    }
}