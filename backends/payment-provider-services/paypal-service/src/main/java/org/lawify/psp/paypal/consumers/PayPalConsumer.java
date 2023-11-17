package org.lawify.psp.paypal.consumers;
import org.lawify.psp.contracts.requestMessages.PaymentMessage;
import org.springframework.jms.annotation.JmsListener;

import org.springframework.stereotype.Component;

@Component
public class PayPalConsumer {
    @JmsListener(destination = "pay_pal_queue")
    public void receiveMessage(PaymentMessage message){
        System.out.println("Received: " + message.Amount);
    }
}
