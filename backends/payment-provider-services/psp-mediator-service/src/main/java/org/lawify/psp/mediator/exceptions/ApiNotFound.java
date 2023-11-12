package org.lawify.psp.mediator.exceptions;

public class ApiNotFound extends RuntimeException {
    public ApiNotFound(String message) {
        super(message);
    }
}
