package org.lawify.psp.contracts.requests;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;
import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class PaymentCommonResponse {
    private Date timeStamp;
    private String redirectUrl;
    private UUID correlationId;
    private String appName;
    private boolean isBankService;
}
