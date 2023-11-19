package org.lawify.psp.mediator.merchants;

import lombok.RequiredArgsConstructor;
import org.lawify.psp.mediator.exceptions.ApiException;
import org.lawify.psp.mediator.merchants.dto.MerchantRequest;
import org.lawify.psp.mediator.utils.ResponseBuilder;
import org.lawify.psp.mediator.utils.validators.BankAccountValidator;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.ZoneId;
import java.time.ZonedDateTime;

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
