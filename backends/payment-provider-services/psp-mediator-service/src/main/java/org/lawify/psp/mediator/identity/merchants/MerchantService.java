package org.lawify.psp.mediator.identity.merchants;

import lombok.RequiredArgsConstructor;
import org.lawify.psp.mediator.shared.crypto.hash.HashService;
import org.lawify.psp.mediator.shared.exceptions.ApiBadRequest;
import org.lawify.psp.mediator.identity.merchants.dto.RegisterMerchantRequest;
import org.lawify.psp.mediator.shared.utils.validators.BankAccountValidator;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MerchantService {
    private final MerchantRepository repository;
    private final BankAccountValidator bankAccountValidator;
    private final PasswordEncoder passwordEncoder;

    public Merchant create(RegisterMerchantRequest request) {
        if (!bankAccountValidator.test(request.getBankAccount())) {
            throw new ApiBadRequest("Bank account is not valid");
        }
        if (repository.findByName(request.getName()).isPresent()) {
            throw new ApiBadRequest("Merchant with name: " + request.getName() + "already exists");
        }
        if(repository.findByEmail(request.getUsername()).isPresent()){
            throw new ApiBadRequest("Merchant with email: " + request.getUsername() + "already exists");

        }
        var newMerchant = new Merchant(
                request.getName(),
                request.getBankAccount(),
                request.getUsername(),
                passwordEncoder.encode(request.getPassword())
        );
        return repository.save(newMerchant);
    }
}
