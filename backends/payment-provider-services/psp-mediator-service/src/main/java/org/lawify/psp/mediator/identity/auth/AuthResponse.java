package org.lawify.psp.mediator.identity.auth;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class AuthResponse {
    public String token;
    public String tokenType;
    public int notBeforePolicy;
    public String scope;

}
