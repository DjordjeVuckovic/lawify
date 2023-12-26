package org.lawify.psp.mediator.transactions.dto;

import lombok.Builder;
import org.lawify.psp.mediator.subscriptionServices.dto.SubscriptionServiceDto;

import java.util.List;

@Builder
public class TransactionResponse {
    public TransactionDto transaction;
    public String token;
    public List<SubscriptionServiceDto> availableServices;
    public String backAccount;
}
