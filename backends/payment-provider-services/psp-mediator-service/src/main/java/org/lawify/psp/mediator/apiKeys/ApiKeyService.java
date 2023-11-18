package org.lawify.psp.mediator.apiKeys;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.lawify.psp.mediator.apiKeys.dto.ApiKeyRequest;
import org.lawify.psp.mediator.apiKeys.dto.ApiKeyResponse;
import org.lawify.psp.mediator.crypto.CryptoService;
import org.lawify.psp.mediator.crypto.converters.CryptoConverter;
import org.lawify.psp.mediator.exceptions.ApiNotFound;
import org.lawify.psp.mediator.merchants.MerchantRepository;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.UUID;

@Service
@Transactional
@RequiredArgsConstructor
public class ApiKeyService {
    private final MerchantRepository merchantRepository;
    private final ApiKeyRepository apiKeyRepository;
    public ApiKeyResponse create(ApiKeyRequest apiKeyDto){
        var merchant = merchantRepository
                .findById(apiKeyDto.merchantId)
                .orElseThrow(() -> new ApiNotFound("Cannot find merchant with id " + apiKeyDto.merchantId));

        var apiKey = ApiKey.builder()
                .key(generateKey())
                .createdAt(new Date())
                .merchant(merchant)
                .expiredAt(apiKeyDto.expiresAt)
                .build();

        var savedApiKey = apiKeyRepository.save(apiKey);

        return new ApiKeyResponse(apiKey.getKey(),savedApiKey.getId());
    }

    public boolean isValid(String key){
        var apiKey = apiKeyRepository.findByKey(key);
        return apiKey.isPresent();
    }
    private String generateKey(){
        return UUID.randomUUID() + "==" + UUID.randomUUID();
    }
}
