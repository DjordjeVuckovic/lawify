package org.lawify.psp.crypto.consumers;

import jakarta.jms.Message;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.lawify.psp.contracts.requests.PaymentCommonResponse;
import org.lawify.psp.contracts.requests.PaymentMessage;
import org.lawify.psp.crypto.orders.OrderService;
import org.lawify.psp.crypto.orders.dtos.CreateOrderRequest;
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

    @SneakyThrows
    @JmsListener(destination = "crypto-service-queue")
    public void receiveMessage(final Message message){
        var converter = jmsTemplate.getMessageConverter();
        var paymentMessage = (PaymentMessage) Objects.requireNonNull(converter).fromMessage(message);
        var request = CreateOrderRequest.builder()
                .priceCurrency("USD")
                .callbackUrl("https://746d-188-120-96-66.ngrok-free.app/api/v1/transactions/notify")
                .receiveCurrency("BTC")
                .transactionId(paymentMessage.transactionId)
                .priceAmount(0.11)
                .merchantId(paymentMessage.merchantId)
                .build();
        var orderResponse = orderService.createOrder(request);
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
