package org.lawify.psp.contracts.requests;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;
import java.util.UUID;
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PaymentBankRequest {
    private UUID MerchantId;
    private UUID MerchantOrderId;
    private Date MerchantTimeStamp;
    private String SuccessUrl;
    private String FailedUrl;
    private String ErrorUrl;
    private BigDecimal Amount;
}
