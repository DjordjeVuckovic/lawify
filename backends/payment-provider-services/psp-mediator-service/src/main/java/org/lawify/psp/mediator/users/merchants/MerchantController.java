package org.lawify.psp.mediator.users.merchants;

import lombok.RequiredArgsConstructor;
import org.lawify.psp.mediator.users.merchants.dto.MerchantRequest;
import org.lawify.psp.mediator.shared.utils.ResponseBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/psp-mediator-service/api/v1/merchants")
@RequiredArgsConstructor
public class MerchantController {
    private final MerchantService service;
    @PostMapping
    public ResponseEntity<?> create(@RequestBody MerchantRequest request){
        var entity = service.create(request);
        return ResponseEntity
                .created(ResponseBuilder.buildCreateUri(entity.getId()))
                .build();
    }
    @GetMapping
    public ResponseEntity<?> getAll(){
        return ResponseEntity
                .ok()
                .build();
    }
}
