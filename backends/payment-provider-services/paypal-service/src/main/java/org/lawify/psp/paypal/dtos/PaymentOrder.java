package org.lawify.psp.paypal.dtos;

import lombok.*;

@RequiredArgsConstructor
@NoArgsConstructor
@Data
@AllArgsConstructor
public class PaymentOrder {
    @NonNull
    private String status;
    private String orderId;
    private String redirectUrl;

}
