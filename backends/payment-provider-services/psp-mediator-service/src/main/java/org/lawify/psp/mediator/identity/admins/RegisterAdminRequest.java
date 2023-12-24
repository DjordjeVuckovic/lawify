package org.lawify.psp.mediator.identity.admins;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class RegisterAdminRequest {
    public String username;
    public String password;
}
