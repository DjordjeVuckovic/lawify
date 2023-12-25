package org.lawify.psp.paypal.consumers;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.lawify.psp.contracts.requests.CompleteOrderRequest;
import org.lawify.psp.contracts.requests.PaymentCommonResponse;
import org.lawify.psp.contracts.requests.PaymentMessage;
import org.lawify.psp.contracts.requests.UpdateTransactionStatus;
import org.lawify.psp.paypal.payPalConnection.PayPalConnectionService;
import org.lawify.psp.paypal.services.PayPalOperationService;
import org.lawify.psp.paypal.services.dtos.CreateOrderRequest;
import org.springframework.jms.annotation.JmsListener;
import jakarta.jms.Message;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.support.converter.MessageConverter;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.Objects;

@Component
@RequiredArgsConstructor
public class PaymentConsumer {
    private final JmsTemplate jmsTemplate;
    private final PayPalOperationService payPalService;
    private final PayPalConnectionService paypalConnectionService;
    @SneakyThrows
    @JmsListener(destination = "paypal-service-queue")
    public void receiveMessage(final Message message){
        MessageConverter converter = jmsTemplate.getMessageConverter();
        PaymentMessage paymentMessage = (PaymentMessage) Objects.requireNonNull(converter).fromMessage(message);
        System.out.println(paymentMessage);
        var paypalConnection = paypalConnectionService
                .getPayPalConnectionByUserId(paymentMessage.merchantId);
        var createOrderRequest = CreateOrderRequest
                .builder()
                .fee(paymentMessage.amount)
                .transactionId(paymentMessage.transactionId)
                .email(paypalConnection.getPayPalEmail())
                .build();
        var responseFromPayPal = payPalService
                .createOrder(createOrderRequest);
        var response = new PaymentCommonResponse();
        response.setRedirectUrl(responseFromPayPal.getRedirectUrl());
        response.setAppName("paypal-service");
        response.setTimeStamp(new Date());
        response.setBankService(false);

        jmsTemplate.send(
                message.getJMSReplyTo(),
                session -> Objects.requireNonNull(jmsTemplate.getMessageConverter())
                        .toMessage(response,session)
        );
    }
    @SneakyThrows
    @JmsListener(destination = "paypal-service-queue-complete")
    public void receiveCompleteOrderMessage(final Message message){
        var converter = jmsTemplate.getMessageConverter();
        var paymentMessage = (CompleteOrderRequest) Objects.requireNonNull(converter).fromMessage(message);
        System.out.println(paymentMessage);
        var responseFromPayPal = payPalService.completeOrder(paymentMessage.token);
        var response = new UpdateTransactionStatus();
        response.setStatus(responseFromPayPal.getStatus());
        response.setTransactionId(responseFromPayPal.getTransactionId());
        jmsTemplate.send(
                message.getJMSReplyTo(),
                session -> Objects.requireNonNull(jmsTemplate.getMessageConverter())
                        .toMessage(response,session)
        );
    }
}
