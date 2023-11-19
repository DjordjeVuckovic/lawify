package org.lawify.psp.mediator.payments;

import lombok.RequiredArgsConstructor;
import org.lawify.psp.mediator.apiKeys.ApiKeyService;
import org.lawify.psp.mediator.shared.exceptions.ApiUnauthorized;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PaymentService {
    private final ApiKeyService apiKeyService;
}
