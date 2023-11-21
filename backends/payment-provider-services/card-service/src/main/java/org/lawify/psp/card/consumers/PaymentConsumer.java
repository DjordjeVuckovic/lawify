package org.lawify.psp.card.consumers;

import jakarta.jms.Message;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.lawify.psp.card.clients.BankClient;
import org.lawify.psp.contracts.requests.PaymentCommonResponse;
import org.lawify.psp.contracts.requests.PaymentMessage;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.Objects;

@Component
@RequiredArgsConstructor
@Slf4j
public class PaymentConsumer {
    private final JmsTemplate jmsTemplate;
    private final BankClient bankClient;
    @SneakyThrows
    @JmsListener(destination = "card-service-queue")
    public void receiveMessage(final Message message) {

        var converter = jmsTemplate.getMessageConverter();
        var paymentMessage = (PaymentMessage) Objects.requireNonNull(converter).fromMessage(message);
        System.out.println(paymentMessage);
        var url = bankClient.getTransactionResponse(paymentMessage);
        var response = new PaymentCommonResponse();
        response.setAppName("card-service");
        response.setTimeStamp(new Date());
        response.setBankService(true);
        response.setCorrelationId(paymentMessage.merchantId);
        response.setRedirectUrl(url);

        jmsTemplate.send(
                message.getJMSReplyTo(),
                session -> Objects.requireNonNull(jmsTemplate.getMessageConverter())
                        .toMessage(response, session)
        );
    }
}
