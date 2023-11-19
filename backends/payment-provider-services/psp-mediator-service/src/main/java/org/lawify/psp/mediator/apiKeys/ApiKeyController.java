package org.lawify.psp.mediator.apiKeys;

import jakarta.ws.rs.PathParam;
import lombok.RequiredArgsConstructor;
import org.lawify.psp.mediator.apiKeys.dto.ApiKeyRequest;
import org.lawify.psp.mediator.utils.ResponseBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/psp-mediator-service/api/v1/api-keys")
@RequiredArgsConstructor
public class ApiKeyController {
    private final ApiKeyService service;

    @PostMapping()
    public ResponseEntity<?> create(@RequestBody ApiKeyRequest request) {
        var response = service.create(request);
        return ResponseEntity
                .created(ResponseBuilder.buildCreateUri(response.id))
                .body(response);
    }

    @GetMapping("/validate/{api-key}")
    public ResponseEntity<?> validate(@PathVariable("api-key") String key) {
        var isValid = service.isValid(key);
        return isValid
                ? ResponseEntity.ok().build()
                : ResponseEntity.notFound().build();
    }
}
