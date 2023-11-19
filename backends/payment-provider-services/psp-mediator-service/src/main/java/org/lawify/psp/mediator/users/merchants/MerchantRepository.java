package org.lawify.psp.mediator.users.merchants;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;
@Repository
public interface MerchantRepository extends JpaRepository<Merchant, UUID> {
    Optional<Merchant> findByName(String name);
    Optional<Merchant> findByEmail(String name);
}
