package org.lawify.psp.mediator.shared.exceptions;

public class ApiConflict extends RuntimeException {
    public ApiConflict(String message) {
        super(message);
    }
}
