package org.lawify.psp.mediator.users.merchants.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MerchantRequest {
    private String username;
    private String password;
    private String name;
    private String bankAccount;
}
