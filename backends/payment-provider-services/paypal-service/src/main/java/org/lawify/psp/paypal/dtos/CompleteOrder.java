package org.lawify.psp.paypal.dtos;

import lombok.*;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@RequiredArgsConstructor
public class CompleteOrder {
    @NonNull
    private String status;
    private UUID transactionId;
}
