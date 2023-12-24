package org.lawify.psp.paypal.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@AllArgsConstructor
public class CompleteOrder {
    private String status;
}
