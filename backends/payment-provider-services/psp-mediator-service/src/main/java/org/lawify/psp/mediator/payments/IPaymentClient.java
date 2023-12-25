package org.lawify.psp.mediator.payments;


public interface IPaymentClient {
    PspPaymentIntend createPaymentIntent(double amountInEur, String email);
}
