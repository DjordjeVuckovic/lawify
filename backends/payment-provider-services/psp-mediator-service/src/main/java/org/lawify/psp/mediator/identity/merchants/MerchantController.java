package org.lawify.psp.mediator.identity.merchants;

import lombok.RequiredArgsConstructor;
import org.lawify.psp.mediator.identity.annotations.MerchantRole;
import org.springframework.context.annotation.Role;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/psp-mediator-service/api/v1/merchants")
@RequiredArgsConstructor
public class MerchantController {
    private final MerchantService service;
    @GetMapping
    @MerchantRole
    public ResponseEntity<?> getAll(){
        return ResponseEntity
                .ok()
                .build();
    }
}
