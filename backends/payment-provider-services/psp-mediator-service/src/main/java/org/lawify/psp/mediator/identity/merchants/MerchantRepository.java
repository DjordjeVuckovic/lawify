package org.lawify.psp.mediator.identity.merchants;

import org.lawify.psp.mediator.subscriptionServices.SubscriptionServiceEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
@Repository
public interface MerchantRepository extends JpaRepository<Merchant, UUID> {
    Optional<Merchant> findByName(String name);
    Optional<Merchant> findByEmail(String name);
    @Query("select m.subscriptionServices from Merchant m where m.id = ?1")
    List<SubscriptionServiceEntity> findSubscriptions(UUID merchantId);
}
