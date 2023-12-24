package org.lawify.psp.mediator.shared.jwt;

import org.lawify.psp.mediator.identity.UserBase;
import org.springframework.security.core.userdetails.UserDetails;

public interface IJwtService {
    String extractUsername(String token);

    String generateToken(UserBase user);

    boolean isTokenValid(String token, UserDetails userDetails);
}
