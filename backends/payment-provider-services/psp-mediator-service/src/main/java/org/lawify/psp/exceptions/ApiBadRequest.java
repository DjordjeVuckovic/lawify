package org.lawify.psp.exceptions;

public class ApiBadRequest extends RuntimeException {
    public ApiBadRequest(String message) {
        super(message);
    }
}
