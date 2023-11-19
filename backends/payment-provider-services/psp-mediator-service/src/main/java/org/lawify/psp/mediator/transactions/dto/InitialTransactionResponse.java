package org.lawify.psp.mediator.transactions.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class InitialTransactionResponse {
    private String pspUrl;
    private UUID id;
}
