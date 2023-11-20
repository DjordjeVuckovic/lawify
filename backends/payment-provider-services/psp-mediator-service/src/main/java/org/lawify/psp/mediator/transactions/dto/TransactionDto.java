package org.lawify.psp.mediator.transactions.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.lawify.psp.mediator.subscriptionServices.SubscriptionService;
import org.lawify.psp.mediator.transactions.TransactionStatus;

import java.math.BigDecimal;
import java.util.Date;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TransactionDto {
    private UUID id;
    private UUID orderId;
    private Date timeStamp;
    private UUID merchantId;
    private BigDecimal amount;
    private String status;
}
