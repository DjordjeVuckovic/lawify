package org.lawify.psp.paypal.transactions;

import lombok.RequiredArgsConstructor;
import org.lawify.psp.paypal.shared.exceptions.ApiNotFound;
import org.lawify.psp.paypal.transactions.dtos.CreateTransactionRequest;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TransactionService {
    private final TransactionRepository transactionRepository;
    public void saveTransaction(CreateTransactionRequest request){
        var transaction = Transaction
                .builder()
                .transactionId(request.getTransactionId())
                .orderId(request.getOrderId())
                .build();
        transactionRepository.save(transaction);
    }
    public Transaction findByOrderId(String orderId){
        return transactionRepository.findByOrderId(orderId)
                .orElseThrow(()-> new ApiNotFound("Transaction not found!"));
    }

}
