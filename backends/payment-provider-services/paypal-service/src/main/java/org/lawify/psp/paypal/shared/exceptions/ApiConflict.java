package org.lawify.psp.paypal.shared.exceptions;

public class ApiConflict extends RuntimeException {
    public ApiConflict(String message) {
        super(message);
    }
}
