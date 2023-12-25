package org.lawify.psp.mediator.consumers;

import jakarta.jms.Message;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.lawify.psp.contracts.requests.UpdateTransactionStatus;
import org.lawify.psp.mediator.transactions.PaymentTransactionService;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.support.converter.MessageConverter;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
@RequiredArgsConstructor
public class TransactionConsumer {
    private final JmsTemplate jmsTemplate;
    private final PaymentTransactionService transactionService;
    @SneakyThrows
    @JmsListener(destination = "transaction-status-queue")
    public void receiveMessage(final Message message){
        MessageConverter converter = jmsTemplate.getMessageConverter();
        var updateTransactionStatus = (UpdateTransactionStatus) Objects.requireNonNull(converter).fromMessage(message);
       transactionService.updateStatus(updateTransactionStatus);
        System.out.println(updateTransactionStatus);
    }
}
