package org.lawify.psp.mediator.shared.exceptions;

public class ApiNotFound extends RuntimeException {
    public ApiNotFound(String message) {
        super(message);
    }
}
