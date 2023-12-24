package org.lawify.psp.paypal.shared.exceptions;

public class ApiUnauthorized extends RuntimeException {
    public ApiUnauthorized(String message) {
        super(message);
    }
}