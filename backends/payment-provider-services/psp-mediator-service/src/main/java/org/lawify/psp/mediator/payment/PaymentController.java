package org.lawify.psp.mediator.payment;

import org.lawify.psp.contracts.requests.PaymentCommonResponse;
import org.lawify.psp.contracts.requests.PaymentMessage;
import lombok.RequiredArgsConstructor;
import org.lawify.psp.blocks.broker.IMessageBroker;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController("")
@RequiredArgsConstructor
public class PaymentController {
    private final IMessageBroker messageBroker;
    @PostMapping(value = "")
    public ResponseEntity<?> processPayment(@RequestBody PaymentRequest request){
        messageBroker.send("pay_pal_queue",new PaymentMessage(request.Amount));
        var msg = messageBroker.sendAndReceive(
                "card-service",
                new PaymentMessage(request.Amount),
                PaymentCommonResponse.class);
        System.out.println(msg.getMessage());
        return ResponseEntity.ok().build();
    }
}
