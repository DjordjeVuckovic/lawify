package org.lawify.psp.mediator.consumers;

import org.lawify.psp.contracts.requestMessages.PaymentMessage;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

@Component
public class PaymentConsumerSameApp {
    @JmsListener(destination = "queue")
    public void receiveMessage(PaymentMessage message) {

        System.out.println("Received: " + message.Amount);
    }
}
