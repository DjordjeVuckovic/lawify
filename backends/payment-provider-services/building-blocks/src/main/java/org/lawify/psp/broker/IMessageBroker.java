package org.lawify.psp.broker;

public interface IMessageBroker {
    <T> void send(String destination, T message);
}
