package org.lawify.psp.mediator.identity.auth;

import lombok.RequiredArgsConstructor;
import org.lawify.psp.mediator.identity.UserBase;
import org.lawify.psp.mediator.identity.UserService;
import org.lawify.psp.mediator.identity.admins.RegisterAdminRequest;
import org.lawify.psp.mediator.identity.admins.SysAdminService;
import org.lawify.psp.mediator.identity.merchants.MerchantService;
import org.lawify.psp.mediator.identity.merchants.dto.RegisterMerchantRequest;
import org.lawify.psp.mediator.shared.exceptions.ApiBadRequest;
import org.lawify.psp.mediator.shared.exceptions.ApiUnauthorized;
import org.lawify.psp.mediator.shared.jwt.JwtService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final MerchantService merchantService;
    private final SysAdminService sysAdminService;
    private final JwtService jwtService;
    private final UserService userService;
    private final AuthenticationManager authenticationManager;
    public AuthResponse registerMerchant(RegisterMerchantRequest registerMerchantRequest){
        var merchant = merchantService.create(registerMerchantRequest);
        return generatePayload(merchant);
    }

    public AuthResponse registerAdmin(RegisterAdminRequest request){
        var merchant = sysAdminService.create(request);
        return generatePayload(merchant);
    }

    public AuthResponse auth(AuthRequest authRequest){
        var userOrNull = userService.findByUsername(authRequest.getMail());
        if(userOrNull == null) {
            throw new ApiUnauthorized("User credentials is not valid!");
        }
        try{
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            authRequest.getMail(),
                            authRequest.getPassword()
                    )
            );
        }
        catch (Exception e){
            throw new ApiUnauthorized("User credentials is not valid");
        }
        return generatePayload(userOrNull);

    }

    private AuthResponse generatePayload(UserBase user){
        var jwtAccessToken = jwtService.generateToken(user);
        return AuthResponse.builder()
                .token(jwtAccessToken)
                .tokenType("Bearer")
                .notBeforePolicy(0)
                .scope("email profile")
                .build();
    }
}
