package org.lawify.psp.mediator.subscriptionServices;

import lombok.RequiredArgsConstructor;
import org.lawify.psp.mediator.identity.annotations.MerchantRole;
import org.lawify.psp.mediator.subscriptionServices.dto.SubscriptionServiceDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/psp-mediator-service/api/v1/subscriptions")
@RequiredArgsConstructor
public class SubscriptionServiceController {
    private final SubscriptionHandleService service;

    @GetMapping
    public ResponseEntity<List<SubscriptionServiceDto>> getAllAvailable() {
        var subs = service.findAll();
        return ResponseEntity.ok(subs);
    }
    @GetMapping("merchants/{id}")
    @MerchantRole
    public ResponseEntity<List<SubscriptionServiceDto>> getMerchantSubscriptions(@PathVariable String id) {
        var subs = service.findAll();
        return ResponseEntity.ok(subs);
    }
}
