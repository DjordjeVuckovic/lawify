package org.lawify.psp.mediator.exceptions;

public class ApiConflict extends RuntimeException {
    public ApiConflict(String message) {
        super(message);
    }
}
