package org.lawify.psp.mediator.transactions;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.lawify.psp.mediator.shared.utils.ResponseBuilder;
import org.lawify.psp.mediator.transactions.dto.FinishTransactionRequest;
import org.lawify.psp.mediator.transactions.dto.InitialTransactionRequest;
import org.lawify.psp.mediator.transactions.dto.TransactionDto;
import org.lawify.psp.mediator.transactions.dto.TransactionRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController()
@RequestMapping("/psp-mediator-service/api/v1/transactions")
@RequiredArgsConstructor
public class PaymentTransactionController {
    private final PaymentTransactionService transactionService;
    @PostMapping()
    public ResponseEntity<?> processTransaction(
            @RequestBody InitialTransactionRequest request,
            @RequestHeader("X-API-KEY") String apiKeyHeader
    ){
        if(apiKeyHeader.isEmpty()){
            return ResponseBuilder
                    .buildResponse("Api key is missing", HttpStatus.UNAUTHORIZED);
        }
        var transactionResponse = transactionService.createInitial(request,apiKeyHeader);
        return ResponseEntity
                .created(ResponseBuilder.buildCreateUri(transactionResponse.getId()))
                .body(transactionResponse);
    }
    @GetMapping("{id}")
    public ResponseEntity<TransactionDto> get(@NonNull @PathVariable UUID id){
        return ResponseEntity.ok(transactionService.getStartedTransaction(id));
    }
    @PostMapping("/payment")
    public ResponseEntity<?> finalizeTransaction(
            @RequestBody TransactionRequest request
    ){
        var transactionResponse = transactionService.processPayment(request.getTransactionId(),request.getSubscriptionId());
        return ResponseEntity.ok(transactionResponse);
    }
    @PostMapping("/complete-transaction")
    public ResponseEntity<?> completeTransaction(
            @RequestBody FinishTransactionRequest request
            ){
        var transactionResponse = transactionService.completePayment(request.getToken());
        return ResponseEntity.ok(transactionResponse);
    }
}
