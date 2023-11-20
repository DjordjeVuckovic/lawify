package org.lawify.psp.card.consumers;
import jakarta.jms.Message;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.lawify.psp.contracts.requests.PaymentCommonResponse;
import org.lawify.psp.contracts.requests.PaymentMessage;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.support.converter.MessageConverter;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.Objects;

@Component
@RequiredArgsConstructor
@Slf4j
public class PaymentConsumer {
    private final JmsTemplate jmsTemplate;
    @SneakyThrows
    @JmsListener(destination = "card-service-queue")
    public void receiveMessage(final Message message) {

        MessageConverter converter = jmsTemplate.getMessageConverter();
        PaymentMessage paymentMessage = (PaymentMessage) Objects.requireNonNull(converter).fromMessage(message);
        System.out.println(paymentMessage);

        PaymentCommonResponse response = new PaymentCommonResponse();
        response.setAppName("card-service");
        response.setTimeStamp(new Date());
        response.setBankService(true);

        jmsTemplate.send(
                message.getJMSReplyTo(),
                session -> Objects.requireNonNull(jmsTemplate.getMessageConverter())
                        .toMessage(response, session)
        );
    }
}
