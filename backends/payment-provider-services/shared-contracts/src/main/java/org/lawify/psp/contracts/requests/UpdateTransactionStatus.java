package org.lawify.psp.contracts.requests;

import lombok.*;

import java.util.UUID;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UpdateTransactionStatus {
    public UUID transactionId;
    public String status;
}
