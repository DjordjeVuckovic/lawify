package org.lawify.psp.qr.consumers;

import jakarta.jms.Message;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.lawify.psp.contracts.requests.PaymentCommonResponse;
import org.lawify.psp.contracts.requests.PaymentMessage;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.support.converter.MessageConverter;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.Objects;

@Component
@RequiredArgsConstructor
public class PaymentConsumer {
    private final JmsTemplate jmsTemplate;
    @SneakyThrows
    @JmsListener(destination = "qrcode-service-queue")
    public void receiveMessage(final Message message){
        MessageConverter converter = jmsTemplate.getMessageConverter();
        PaymentMessage paymentMessage = (PaymentMessage) Objects.requireNonNull(converter).fromMessage(message);
        System.out.println(paymentMessage);

        var response = new PaymentCommonResponse();
        response.setAppName("qr-code-service");
        response.setTimeStamp(new Date());
        response.setBankService(true);

        jmsTemplate.send(
                message.getJMSReplyTo(),
                session -> Objects.requireNonNull(jmsTemplate.getMessageConverter())
                        .toMessage(response,session)
        );
    }
}
