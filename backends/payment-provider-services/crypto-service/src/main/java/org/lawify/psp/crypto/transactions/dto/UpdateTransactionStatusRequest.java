package org.lawify.psp.crypto.transactions.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UpdateTransactionStatusRequest {
    private String order_id;
    private String status;

}
