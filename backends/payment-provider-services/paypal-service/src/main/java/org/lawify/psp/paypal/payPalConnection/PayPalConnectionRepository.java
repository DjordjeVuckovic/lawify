package org.lawify.psp.paypal.payPalConnection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface PayPalConnectionRepository extends JpaRepository<PayPalConnection, UUID> {
    @Query("select p from PayPalConnection p where p.UserId = ?1")
    Optional<PayPalConnection> findByUserId(UUID userId);
}
