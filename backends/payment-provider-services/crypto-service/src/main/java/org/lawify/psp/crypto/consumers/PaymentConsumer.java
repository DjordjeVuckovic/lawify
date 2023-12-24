package org.lawify.psp.crypto.consumers;

import jakarta.jms.Message;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.lawify.psp.contracts.requests.PaymentCommonResponse;
import org.lawify.psp.contracts.requests.PaymentMessage;
import org.lawify.psp.crypto.accounts.AccountRepository;
import org.lawify.psp.crypto.accounts.AccountService;
import org.lawify.psp.crypto.accounts.dtos.CreateAccountRequest;
import org.lawify.psp.crypto.orders.OrderService;
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
    private final OrderService orderService;
    private final AccountService accountService;
    @SneakyThrows
    @JmsListener(destination = "crypto-service-queue")
    public void receiveMessage(final Message message){
        var converter = jmsTemplate.getMessageConverter();
        var paymentMessage = (PaymentMessage) Objects.requireNonNull(converter).fromMessage(message);
        System.out.println(paymentMessage);
        var orderResponse = orderService.createOrder();
        var response = new PaymentCommonResponse();
        response.setAppName("crypto-service");
        response.setRedirectUrl(orderResponse.getPaymentUrl());
        response.setTimeStamp(new Date());
        response.setBankService(false);

        jmsTemplate.send(
                message.getJMSReplyTo(),
                session -> Objects.requireNonNull(jmsTemplate.getMessageConverter())
                        .toMessage(response,session)
        );
    }
}
