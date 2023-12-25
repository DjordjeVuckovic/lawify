package org.lawify.psp.paypal.transactions.dtos;

import lombok.*;

import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CreateTransactionRequest {
    private UUID transactionId;
    private String orderId;
}
