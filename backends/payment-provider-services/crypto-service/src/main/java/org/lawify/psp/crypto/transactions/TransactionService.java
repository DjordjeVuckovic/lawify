package org.lawify.psp.crypto.transactions;

import lombok.RequiredArgsConstructor;
import org.lawify.psp.blocks.broker.IMessageBroker;
import org.lawify.psp.contracts.requests.UpdateTransactionStatus;
import org.lawify.psp.crypto.shared.converters.StatusConverter;
import org.lawify.psp.crypto.shared.exceptions.ApiNotFound;
import org.lawify.psp.crypto.transactions.dto.CreateTransactionRequest;
import org.lawify.psp.crypto.transactions.dto.UpdateTransactionStatusRequest;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TransactionService {
    private final TransactionRepository transactionRepository;
    private final IMessageBroker messageBroker;

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
        var status = StatusConverter.convertToStatus(request.getStatus());
        messageBroker.send(
                "transaction-status-queue",
                new UpdateTransactionStatus(
                        transaction.getTransactionId(),
                       status
                ));
    }
}
