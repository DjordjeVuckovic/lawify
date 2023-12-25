package org.lawify.psp.mediator.subscriptionServices;

import lombok.RequiredArgsConstructor;
import org.lawify.psp.mediator.identity.annotations.MerchantRole;
import org.lawify.psp.mediator.payments.PspPaymentIntend;
import org.lawify.psp.mediator.subscriptionServices.dto.SubscriptionPayment;
import org.lawify.psp.mediator.subscriptionServices.dto.SubscriptionServiceDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

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
    @GetMapping("users/{id}")
    @MerchantRole
    public ResponseEntity<List<SubscriptionServiceDto>> getSubscriptions(@PathVariable UUID id) {
        var subs = service.findByUser(id);
        return ResponseEntity.ok(subs);
    }
    @GetMapping("/available/users/{id}")
    @MerchantRole
    public ResponseEntity<List<SubscriptionServiceDto>> getAvailableSubscriptions(@PathVariable UUID id) {
        var subs = service.findAvailableByUser(id);
        return ResponseEntity.ok(subs);
    }
    @PostMapping("payment-intent")
    public ResponseEntity<PspPaymentIntend> processPaymentIntent(@RequestBody SubscriptionPayment request) {
        var response = service.handleNewPayment(request);
        return ResponseEntity.ok(response);
    }
}
