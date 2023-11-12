package org.lawify.psp.payment;

import com.lawify.psp.requests.PaymentMessage;
import lombok.RequiredArgsConstructor;
import org.lawify.psp.broker.IMessageBroker;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController("")
@RequiredArgsConstructor
public class PaymentController {
    private final IMessageBroker jmsTemplate;
    @PostMapping(value = "")
    public ResponseEntity<?> processPayment(@RequestBody PaymentRequest request){
        jmsTemplate.send("card-service-queue", new PaymentMessage(request.Amount));
        jmsTemplate.send("queue", new PaymentMessage(request.Amount));
        return ResponseEntity.ok().build();
    }
}
