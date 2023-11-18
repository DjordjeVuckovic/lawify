package org.lawify.psp.mediator.subscriptionServices;

import lombok.RequiredArgsConstructor;
import org.lawify.psp.mediator.subscriptionServices.dto.SubscriptionServiceDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/psp-mediator-service/api/v1/subscriptions")
@RequiredArgsConstructor
public class SubscriptionServiceController {
    private final SubscriptionServiceRepository repository;

    @GetMapping
    public ResponseEntity<List<SubscriptionServiceDto>> getAllAvailable() {
        var subs = repository.findAll()
                .stream()
                .map(x -> SubscriptionServiceDto
                        .builder()
                        .id(x.getId())
                        .name(x.getName())
                        .build()
                ).toList();
        return ResponseEntity.ok(subs);
    }
}
