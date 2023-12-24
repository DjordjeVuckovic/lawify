package org.lawify.psp.crypto.transactions;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;
@Repository
public interface TransactionRepository extends JpaRepository<Transaction, UUID> {
    @Query("select t from Transaction t where t.cryptoTransactionId = ?1")
    Optional<Transaction> findByOrderId(String id);
}
