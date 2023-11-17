package org.lawify.psp.blocks.broker;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class MessageBroker implements IMessageBroker {
    private final JmsTemplate jmsTemplate;

    public <T> void send(String destination, T message) {
        log.info("Sending message {}",message);
        jmsTemplate.convertAndSend(destination, message);
    }
//    public <TRequest,TResponse> TResponse sendAndReceive(String queueName, TRequest message) {
//        return jmsTemplate.sendAndReceive(queueName, session -> {
//            TextMessage textMessage = session.createTextMessage(message);
//            Destination replyQueue = session.createTemporaryQueue();
//            textMessage.setJMSReplyTo(replyQueue);
//            return textMessage;
//        });
//    }
}
