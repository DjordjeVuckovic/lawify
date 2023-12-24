package org.lawify.psp.mediator.identity;

import lombok.RequiredArgsConstructor;
import org.lawify.psp.mediator.identity.admins.SysAdminRepository;
import org.lawify.psp.mediator.identity.merchants.MerchantRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    private final SysAdminRepository adminRepository;
    private final MerchantRepository merchantRepository;
    public UserBase findByUsername(String username){
        var admin = adminRepository.findByEmail(username);
        var merchant = merchantRepository.findByEmail(username);
        if(merchant.isPresent()){
            return merchant.get();
        }
        return admin.orElse(null);
    }
}
