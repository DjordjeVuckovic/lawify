package org.lawify.psp.mediator.payments;

import org.lawify.psp.contracts.requests.PaymentCommonResponse;
import org.lawify.psp.contracts.requests.PaymentMessage;
import lombok.RequiredArgsConstructor;
import org.lawify.psp.blocks.broker.IMessageBroker;
import org.lawify.psp.mediator.shared.utils.ResponseBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController()
@RequestMapping("/psp-mediator-service/api/v1/payments")
@RequiredArgsConstructor
public class PaymentController {
    private final IMessageBroker messageBroker;
    private final PaymentService paymentService;
    @PostMapping(value = "/mock")
    public ResponseEntity<?> processPaymentMock(@RequestBody PaymentRequestMock request){
        messageBroker.send("pay_pal_queue",new PaymentMessage(request.Amount));
        var msg = messageBroker.sendAndReceive(
                "card-service",
                new PaymentMessage(request.Amount),
                PaymentCommonResponse.class);
        System.out.println(msg.getMessage());
        return ResponseEntity.ok().build();
    }
    @PostMapping()
    public ResponseEntity<?> processPayment(
            @RequestBody PaymentRequest request,
            @RequestHeader("X-API-KEY") String apiKeyHeader
    ){
        if(apiKeyHeader.isEmpty()){
            return ResponseBuilder
                    .buildResponse("Api key is missing", HttpStatus.UNAUTHORIZED);
        }
        return ResponseEntity.ok().build();
    }
}
