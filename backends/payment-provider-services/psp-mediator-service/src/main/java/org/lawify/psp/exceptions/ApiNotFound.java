package org.lawify.psp.exceptions;

public class ApiNotFound extends RuntimeException {
    public ApiNotFound(String message) {
        super(message);
    }
}
