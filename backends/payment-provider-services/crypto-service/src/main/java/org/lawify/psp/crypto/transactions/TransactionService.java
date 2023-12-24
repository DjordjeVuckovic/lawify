package org.lawify.psp.crypto.transactions;

import lombok.RequiredArgsConstructor;
import org.lawify.psp.blocks.broker.IMessageBroker;
import org.lawify.psp.contracts.requests.PaymentCommonResponse;
import org.lawify.psp.contracts.requests.UpdateTransactionStatus;
import org.lawify.psp.crypto.shared.exceptions.ApiNotFound;
import org.lawify.psp.crypto.transactions.dto.CreateTransactionRequest;
import org.lawify.psp.crypto.transactions.dto.UpdateTransactionStatusRequest;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TransactionService {
    private final TransactionRepository transactionRepository;
    private final IMessageBroker messageBroker;

    public Transaction findByOrderId(String orderId){
       var transaction =  transactionRepository.findByOrderId(orderId)
                .orElseThrow(()->new ApiNotFound("Transaction not found!"));
       return transaction;
    }
    public void saveTransaction(CreateTransactionRequest request){
        var transaction = Transaction
                .builder()
                .transactionId(request.getTransactionId())
                .cryptoTransactionId(request.getOrderId())
                .build();
        transactionRepository.save(transaction);
    }
    public void updateTransaction(UpdateTransactionStatusRequest request){
        var transaction = transactionRepository.findByOrderId(request.getOrder_id())
                .orElseThrow(()->new ApiNotFound("Transaction not found!"));
        messageBroker.send(
                "transaction-status-queue",
                new UpdateTransactionStatus(
                        transaction.getTransactionId(),
                        request.getStatus()
                ));
    }
}
