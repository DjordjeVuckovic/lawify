package org.lawify.psp.mediator.transactions;

import lombok.RequiredArgsConstructor;
import org.lawify.psp.mediator.apiKeys.ApiKeyService;
import org.lawify.psp.mediator.shared.exceptions.ApiNotFound;
import org.lawify.psp.mediator.shared.exceptions.ApiUnauthorized;
import org.lawify.psp.mediator.transactions.dto.InitialTransactionRequest;
import org.lawify.psp.mediator.transactions.dto.InitialTransactionResponse;
import org.lawify.psp.mediator.transactions.dto.TransactionDto;
import org.lawify.psp.mediator.transactions.mapper.TransactionMapper;
import org.lawify.psp.mediator.users.merchants.MerchantRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PaymentTransactionService {
    @Value("${payment.fe_base_url}")
    private String feUrl;
    private final PaymentTransactionRepository transactionRepository;
    private final MerchantRepository merchantRepository;
    private final ApiKeyService service;

    public InitialTransactionResponse createInitial(InitialTransactionRequest request, String apiKey) {
        var merchant = merchantRepository.findByEmail(request.getMerchantUsername())
                .orElseThrow(() -> new ApiUnauthorized
                        ("Merchant with username: " + request.getMerchantUsername() + "not found.")
                );
        var isValidKey = service.contains(merchant.getApiKeys(), apiKey);
        if(!isValidKey){
            throw new ApiUnauthorized("Api key is not valid");
        }
        var transaction = PaymentTransaction.builder()
                .orderId(request.getOrderId())
                .status(TransactionStatus.STARTED)
                .merchantId(merchant.getId())
                .timeStamp(new Date())
                .amount(request.getAmount())
                .build();

        transactionRepository.save(transaction);
        return new InitialTransactionResponse(buildFeUrl(transaction.getId()),transaction.getId());
    }
    public TransactionDto get(UUID id){
        var transaction = transactionRepository.findById(id)
                .orElseThrow(() -> new ApiNotFound("Transaction not found"));

        return TransactionMapper.map(transaction);
    }
    private String buildFeUrl(UUID transactionId){
        return feUrl + "/payments?transaction=" + transactionId;
    }

}
