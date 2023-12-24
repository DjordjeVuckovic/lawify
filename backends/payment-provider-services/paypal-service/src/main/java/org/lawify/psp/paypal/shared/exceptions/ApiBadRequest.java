package org.lawify.psp.paypal.shared.exceptions;

public class ApiBadRequest extends RuntimeException {
    public ApiBadRequest(String message) {
        super(message);
    }
}
