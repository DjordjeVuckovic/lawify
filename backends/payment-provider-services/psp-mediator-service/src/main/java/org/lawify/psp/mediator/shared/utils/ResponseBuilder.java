package org.lawify.psp.mediator.shared.utils;

import org.lawify.psp.mediator.shared.exceptions.ApiException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.UUID;

public class ResponseBuilder {
    public static URI buildCreateUri(UUID id){
        return ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(id)
                .toUri();
    }
    public static ResponseEntity<?> buildResponse(String message, HttpStatus code){
        ApiException payload = new ApiException(
                message,
                code,
                ZonedDateTime.now(ZoneId.of("Z"))
        );
        return ResponseEntity.status(code).body(payload);
    }
}
