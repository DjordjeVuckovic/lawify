package org.lawify.psp.mediator.shared.converters;

import org.lawify.psp.mediator.transactions.TransactionStatus;

public class StatusConverter {
    public static TransactionStatus convertToStatus(String status){
        return switch (status) {
            case "Pending" -> TransactionStatus.PENDING;
            case "Success" -> TransactionStatus.SUCCESS;
            default -> TransactionStatus.FAILED;
        };
    }
}
