package org.lawify.psp.mediator.identity;

import lombok.RequiredArgsConstructor;
import org.lawify.psp.mediator.shared.exceptions.ApiNotFound;
import org.lawify.psp.mediator.identity.admins.SysAdminRepository;
import org.lawify.psp.mediator.identity.merchants.MerchantRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PspUserDetailsService implements UserDetailsService {
    private final SysAdminRepository adminRepository;
    private final MerchantRepository merchantRepository;

    @Override
    public UserDetails loadUserByUsername(String username) {
        var admin = adminRepository.findByEmail(username);
        var merchant = merchantRepository.findByEmail(username);
        if(merchant.isPresent()){
            return merchant.get();
        }
        if(admin.isPresent()){
            return admin.get();
        }
        throw new ApiNotFound("User with username: " + username + "not found.");
    }
}
