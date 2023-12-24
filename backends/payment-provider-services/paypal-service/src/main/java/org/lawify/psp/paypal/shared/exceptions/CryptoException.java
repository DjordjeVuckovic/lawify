package org.lawify.psp.paypal.shared.exceptions;

public class CryptoException extends RuntimeException {
    public CryptoException(String message) {
        super(message);
    }
    public CryptoException(String message, Throwable cause) {
        super(message, cause);
    }
}
