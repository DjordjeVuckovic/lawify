package org.lawify.psp.paypal.shared.exceptions;

public class ApiNotFound extends RuntimeException {
    public ApiNotFound(String message) {
        super(message);
    }
}
