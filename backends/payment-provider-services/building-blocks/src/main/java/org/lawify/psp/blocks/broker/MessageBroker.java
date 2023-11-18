package org.lawify.psp.blocks.broker;

import jakarta.jms.JMSException;
import jakarta.jms.Message;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.UUID;

@Component
@RequiredArgsConstructor
@Slf4j
public class MessageBroker implements IMessageBroker {
    private final JmsTemplate jmsTemplate;

    public <T> void send(String destination, T message) {
        log.info("Sending message {}",message);
        jmsTemplate.convertAndSend(destination, message);
    }
    public <T, R> R sendAndReceive(String destination, T request, Class<R> responseType) {
        log.info("Sending {} and receiving message {}",request, responseType);
        Message responseMessage = jmsTemplate.sendAndReceive(destination, session -> {
            Message message = Objects.requireNonNull(jmsTemplate.getMessageConverter()).toMessage(request, session);
            message.setJMSCorrelationID(UUID.randomUUID().toString());
            return message;
        });

        if (responseMessage != null) {
            try {
                Object response = Objects.requireNonNull(jmsTemplate.getMessageConverter()).fromMessage(responseMessage);
                if (responseType.isInstance(response)) {
                    return responseType.cast(response);
                }
            } catch (JMSException e) {
                // handle exception, possibly rethrow as a runtime exception or custom exception
            }
        }
        return null;
    }
}
