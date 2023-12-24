package org.lawify.psp.mediator.identity.auth;

import lombok.RequiredArgsConstructor;
import org.lawify.psp.mediator.identity.merchants.dto.RegisterMerchantRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/psp-mediator-service/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;
    @PostMapping("register")
    public ResponseEntity<AuthResponse> register(@RequestBody RegisterMerchantRequest request){
        var entity = authService.registerMerchant(request);
        return ResponseEntity
                .ok(entity);
    }
    @PostMapping
    public ResponseEntity<AuthResponse> auth(@RequestBody AuthRequest request){
        var entity = authService.auth(request);
        return ResponseEntity
                .ok(entity);
    }
}
