package org.lawify.psp.mediator.transactions.erros;

public class InvalidTransactionStatus extends  RuntimeException {
    public InvalidTransactionStatus(String message) {
        super(message);
    }
}
