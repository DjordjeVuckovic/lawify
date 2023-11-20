package org.lawify.psp.mediator.transactions.mapper;

import org.lawify.psp.mediator.transactions.PaymentTransaction;
import org.lawify.psp.mediator.transactions.dto.TransactionDto;

public class TransactionMapper {
    public static TransactionDto map(PaymentTransaction transaction){
        return TransactionDto
                .builder()
                .amount(transaction.getAmount())
                .id(transaction.getId())
                .timeStamp(transaction.getTimeStamp())
                .status(transaction.statusToString())
                .merchantId(transaction.getMerchantId())
                .orderId(transaction.getOrderId())
                .build();
    }
}
