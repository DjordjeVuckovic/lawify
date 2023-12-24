package org.lawify.psp.crypto.transactions;

import lombok.RequiredArgsConstructor;
import org.lawify.psp.crypto.transactions.dto.UpdateTransactionStatusRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/transactions")
@RequiredArgsConstructor

public class TransactionController {
    private final TransactionService transactionService;
    @PostMapping("/notify")
    public ResponseEntity<?> updateTransactionStatus(UpdateTransactionStatusRequest request){
        transactionService.updateTransaction(request);
        return ResponseEntity
                .noContent()
                .build();
    }
}
