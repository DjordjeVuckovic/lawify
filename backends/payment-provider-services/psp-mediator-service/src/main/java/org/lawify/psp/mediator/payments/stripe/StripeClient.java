package org.lawify.psp.mediator.payments.stripe;

import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.PaymentIntent;
import com.stripe.model.checkout.Session;
import com.stripe.param.PaymentIntentCreateParams;
import com.stripe.param.checkout.SessionCreateParams;
import jakarta.annotation.PostConstruct;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.lawify.psp.mediator.payments.IPaymentClient;
import org.lawify.psp.mediator.payments.PspPaymentIntend;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class StripeClient implements IPaymentClient {
    @Value("${stripe.key}")
    private String stripeApiKey;
    @Value("${payment.fe-base-url}")
    private String clientUrl;
    @Value("${payment.success-url}")
    private String clientSuccessUrl;
    @Value("${payment.failed-url}")
    private String clientFailUrl;

    @SneakyThrows
    @Override
    public PspPaymentIntend createPaymentIntent(double amountInEur, String email) {
        Stripe.apiKey = stripeApiKey;
        log.info(Stripe.apiKey);
        var params = SessionCreateParams.builder()
                .addPaymentMethodType(SessionCreateParams.PaymentMethodType.CARD)
                .setSuccessUrl(clientUrl + "/" + clientSuccessUrl)
                .setCancelUrl(clientUrl + "/" + clientFailUrl)
                .setCustomerEmail(email)
                .setMode(SessionCreateParams.Mode.PAYMENT)
                .addLineItem(
                        SessionCreateParams.LineItem.builder()
                                .setQuantity(1L)
                                .setPriceData(
                                        SessionCreateParams.LineItem.PriceData.builder()
                                                .setCurrency("EUR")
                                                .setUnitAmount((long) amountInEur * 100)
                                                .setProductData(
                                                        SessionCreateParams.LineItem.PriceData.ProductData.builder()
                                                                .setName("Your Product or Service")
                                                                .build()
                                                )
                                                .build()
                                )
                                .build()
                )
                .build();
        Session session = Session.create(params);
        return new PspPaymentIntend(session.getUrl());
    }
}
