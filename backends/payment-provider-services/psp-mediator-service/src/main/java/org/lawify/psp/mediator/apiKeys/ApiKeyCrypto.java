package org.lawify.psp.mediator.apiKeys;

import lombok.RequiredArgsConstructor;
import org.lawify.psp.mediator.crypto.CryptoService;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ApiKeyCrypto {
    private final CryptoService cryptoService;
}
