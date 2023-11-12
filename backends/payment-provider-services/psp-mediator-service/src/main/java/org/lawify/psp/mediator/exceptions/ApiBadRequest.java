package org.lawify.psp.mediator.exceptions;

public class ApiBadRequest extends RuntimeException {
    public ApiBadRequest(String message) {
        super(message);
    }
}
