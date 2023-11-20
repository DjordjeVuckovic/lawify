package org.lawify.psp.contracts.requests;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
public class PaymentMessage {
    public BigDecimal amount;
    public UUID merchantId;
}
