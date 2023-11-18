package org.lawify.psp.mediator.merchants;

import lombok.RequiredArgsConstructor;
import org.lawify.psp.mediator.exceptions.ApiBadRequest;
import org.lawify.psp.mediator.exceptions.ApiException;
import org.lawify.psp.mediator.merchants.dto.MerchantRequest;
import org.lawify.psp.mediator.utils.validators.BankAccountValidator;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.ZoneId;
import java.time.ZonedDateTime;

@Service
@RequiredArgsConstructor
public class MerchantService {
    private final MerchantRepository repository;
    private final BankAccountValidator bankAccountValidator;
    public Merchant create(MerchantRequest request){
        if (!bankAccountValidator.test(request.getBankAccount())){
            throw new ApiBadRequest("Bank account is not valid");
        }
        if (repository.findByName(request.getName()).isPresent()){
            throw new ApiBadRequest("Merchant with name " + request.getName() + "already exists");
        }
        var newMerchant = Merchant
                .builder()
                .name(request.getName())
                .bankAccount(request.getBankAccount())
                .build();
        return repository.save(newMerchant);
    }
}
