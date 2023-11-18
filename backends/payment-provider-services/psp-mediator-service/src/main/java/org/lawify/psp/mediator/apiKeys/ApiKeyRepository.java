package org.lawify.psp.mediator.apiKeys;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface ApiKeyRepository extends JpaRepository<ApiKey, UUID> {
    Optional<ApiKey> findByKey(String key);
    Optional<ApiKey> findByMerchant_Id(UUID id);
}
