package org.lawify.psp.mediator.identity.admins;

import lombok.RequiredArgsConstructor;
import org.lawify.psp.mediator.shared.exceptions.ApiBadRequest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SysAdminService {
    private final SysAdminRepository adminRepository;
    private final PasswordEncoder passwordEncoder;

    public SysAdmin create(RegisterAdminRequest request) {
        if(adminRepository.findByEmail(request.getUsername()).isPresent()){
            throw new ApiBadRequest("Bad credentials!");

        }
        var admin = new SysAdmin(request.username, passwordEncoder.encode(request.password));
        return adminRepository.save(admin);

    }
}
