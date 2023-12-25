package org.lawify.psp.mediator.transactions;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.lawify.psp.blocks.broker.IMessageBroker;
import org.lawify.psp.contracts.requests.CompleteOrderRequest;
import org.lawify.psp.contracts.requests.PaymentCommonResponse;
import org.lawify.psp.contracts.requests.PaymentMessage;
import org.lawify.psp.contracts.requests.UpdateTransactionStatus;
import org.lawify.psp.mediator.apiKeys.ApiKeyService;
import org.lawify.psp.mediator.shared.converters.StatusConverter;
import org.lawify.psp.mediator.shared.exceptions.ApiNotFound;
import org.lawify.psp.mediator.shared.exceptions.ApiUnauthorized;
import org.lawify.psp.mediator.subscriptionServices.SubscriptionServiceRepository;
import org.lawify.psp.mediator.transactions.dto.InitialTransactionRequest;
import org.lawify.psp.mediator.transactions.dto.InitialTransactionResponse;
import org.lawify.psp.mediator.transactions.dto.TransactionDto;
import org.lawify.psp.mediator.transactions.erros.InvalidTransactionStatus;
import org.lawify.psp.mediator.transactions.mapper.TransactionMapper;
import org.lawify.psp.mediator.identity.merchants.MerchantRepository;
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
    private final SubscriptionServiceRepository subscriptionServiceRepository;
    private final IMessageBroker messageBroker;

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
    public TransactionDto getStartedTransaction(UUID id){
        var transaction = transactionRepository.findById(id)
                .orElseThrow(() -> new ApiNotFound("Transaction not found"));

        if(transaction.getStatus() != TransactionStatus.STARTED){
            throw new InvalidTransactionStatus("Transaction already processed");
        }

        return TransactionMapper.map(transaction);
    }
    @Transactional
    public PaymentCommonResponse processPayment(UUID transactionId,UUID subscriptionId){
        var transaction = transactionRepository.findById(transactionId)
                .orElseThrow(() -> new ApiNotFound("Transaction with id: "+ transactionId + "not found."));
        var subService = subscriptionServiceRepository.findById(subscriptionId)
                .orElseThrow(() -> new ApiNotFound("Payment option with id: "+ subscriptionId + "not found."));
        transaction.setService(subService);
        transaction.setStatus(TransactionStatus.PENDING);
        transactionRepository.save(transaction);
        return messageBroker.sendAndReceive(
                subService.getQueueName(),
                new PaymentMessage(transaction.getAmount(),transaction.getMerchantId(),transaction.getId()),
                PaymentCommonResponse.class);
    }
    private String buildFeUrl(UUID transactionId){
        return feUrl + "/payments?transaction=" + transactionId;
    }

    public UpdateTransactionStatus completePayment(String token) {
        var response = messageBroker.sendAndReceive(
                "paypal-service-queue-complete",
                new CompleteOrderRequest(token),
                UpdateTransactionStatus.class);
        updateStatus(response);
        return  response;
    }
    public void updateStatus(UpdateTransactionStatus request){
        var transaction = transactionRepository.findById(request.getTransactionId())
                .orElseThrow(()-> new ApiNotFound("Transaction not found!"));
        var status = StatusConverter.convertToStatus(request.status);
        transaction.setStatus(status);
        transactionRepository.save(transaction);
    }
}
