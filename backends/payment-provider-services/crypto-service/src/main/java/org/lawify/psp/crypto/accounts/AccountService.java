package org.lawify.psp.crypto.accounts;

import lombok.RequiredArgsConstructor;
import org.lawify.psp.crypto.accounts.dtos.CreateAccountRequest;
import org.lawify.psp.crypto.shared.exceptions.ApiNotFound;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AccountService {
    private final AccountRepository accountRepository;
    public void saveAccount(CreateAccountRequest request){
        var account = Account
                .builder()
                .merchantId(request.getMerchantId())
                .apiKey(request.getApiKey())
                .build();
        accountRepository.save(account);
    }

    public Account getAccount(UUID merchantId) {
       return accountRepository.findById(merchantId)
                .orElseThrow(()-> new ApiNotFound("Account not found!"));
    }
}
