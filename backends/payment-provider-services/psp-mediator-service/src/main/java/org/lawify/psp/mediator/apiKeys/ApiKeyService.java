package org.lawify.psp.mediator.apiKeys;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.lawify.psp.mediator.apiKeys.dto.ApiKeyRequest;
import org.lawify.psp.mediator.apiKeys.dto.ApiKeyResponse;
import org.lawify.psp.mediator.shared.crypto.hash.HashService;
import org.lawify.psp.mediator.shared.exceptions.ApiNotFound;
import org.lawify.psp.mediator.identity.merchants.MerchantRepository;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class ApiKeyService {
    private final MerchantRepository merchantRepository;
    private final ApiKeyRepository apiKeyRepository;
    private final HashService hashService;
    public ApiKeyResponse create(ApiKeyRequest apiKeyDto){
        var merchant = merchantRepository
                .findById(apiKeyDto.merchantId)
                .orElseThrow(() -> new ApiNotFound("Cannot find merchant with id " + apiKeyDto.merchantId));

        var key = generateKey();
        log.info("Generated key {}", key);
        var hashKey = hashService.hashFixed(key);
        var apiKey = ApiKey.builder()
                .key(hashKey)
                .createdAt(new Date())
                .merchant(merchant)
                .expiredAt(apiKeyDto.expiresAt)
                .build();

        var savedApiKey = apiKeyRepository.save(apiKey);

        return new ApiKeyResponse(key,savedApiKey.getId());
    }

    public boolean isValid(String key) {
        var hash = hashService.hashFixed(key);
        var keyDb =  apiKeyRepository.findByKey(hash);
        return keyDb.isPresent();
    }
    public boolean contains(List<ApiKey> apiKeys, String apiKey){
        return apiKeys
                .stream()
                .anyMatch(x -> hashService.verifyFixed(apiKey,x.getKey()));
    }
    private String generateKey(){
        return UUID.randomUUID() + "==" + UUID.randomUUID();
    }
}
