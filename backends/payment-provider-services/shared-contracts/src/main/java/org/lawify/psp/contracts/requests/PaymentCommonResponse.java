package org.lawify.psp.contracts.requests;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class PaymentCommonResponse {
    private boolean isSuccess;
    private String message;
    private Date timeStamp;
    private String appName;
}
