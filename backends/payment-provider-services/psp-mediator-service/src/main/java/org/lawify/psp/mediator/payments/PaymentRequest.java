package org.lawify.psp.mediator.payments;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PaymentRequest {
    private UUID merchantId;
    private BigDecimal Amount;
    private UUID paymentId;
}
