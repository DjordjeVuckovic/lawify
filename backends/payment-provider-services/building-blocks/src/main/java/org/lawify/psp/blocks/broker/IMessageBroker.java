package org.lawify.psp.blocks.broker;

public interface IMessageBroker {
    <T> void send(String destination, T message);
    <T, R> R sendAndReceive(String destination, T request, Class<R> responseType);
}
