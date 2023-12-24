package org.lawify.psp.mediator.identity.admins;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface SysAdminRepository extends JpaRepository<SysAdmin, UUID> {
    Optional<SysAdmin> findByEmail(String email);
}
