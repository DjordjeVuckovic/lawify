package org.lawify.psp.mediator.identity.merchants.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegisterMerchantRequest {
    private String username;
    private String password;
    private String name;
    private String bankAccount;
}
