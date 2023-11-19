package org.lawify.psp.mediator.shared.exceptions;

public class ApiBadRequest extends RuntimeException {
    public ApiBadRequest(String message) {
        super(message);
    }
}
