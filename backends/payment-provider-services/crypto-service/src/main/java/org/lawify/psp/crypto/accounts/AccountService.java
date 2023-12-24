package org.lawify.psp.crypto.accounts;

import lombok.RequiredArgsConstructor;
import org.lawify.psp.crypto.accounts.dtos.CreateAccountRequest;
import org.springframework.stereotype.Service;

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
}
