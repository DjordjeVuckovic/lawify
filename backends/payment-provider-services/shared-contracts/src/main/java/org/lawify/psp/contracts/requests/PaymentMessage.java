package org.lawify.psp.contracts.requests;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
@NoArgsConstructor
@AllArgsConstructor
public class PaymentMessage {
    public BigDecimal amount;
}
