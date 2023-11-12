package org.lawify.psp.card.consumers;
import org.lawify.psp.contracts.requestMessages.PaymentMessage;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

@Component
public class PaymentConsumer {
    @JmsListener(destination = "card-service-queue")
    public void receiveMessage(PaymentMessage message) {
        System.out.println("Received: " + message.Amount);
    }
}
