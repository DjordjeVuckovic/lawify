package org.lawify.psp.mediator.payments.stripe;

import com.stripe.Stripe;
import com.stripe.model.checkout.Session;
import com.stripe.param.checkout.SessionCreateParams;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.lawify.psp.mediator.payments.IPaymentClient;
import org.lawify.psp.mediator.payments.PspPaymentIntend;
import org.lawify.psp.mediator.payments.models.PspLineItem;
import org.lawify.psp.mediator.payments.models.UserInfo;
import org.lawify.psp.mediator.subscriptionServices.SubscriptionHandleService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

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
    public PspPaymentIntend createPaymentIntent(UserInfo userInfo, List<PspLineItem> lineItems) {
        Stripe.apiKey = stripeApiKey;
        log.info(Stripe.apiKey);

        var stripeLineItems = lineItems.stream()
                .map(StripeLineItemBuilder::buildStripeLineItem)
                .toList();

        var params = SessionCreateParams.builder()
                .addPaymentMethodType(SessionCreateParams.PaymentMethodType.CARD)
                .setSuccessUrl(clientUrl + "/" + clientSuccessUrl)
                .setCancelUrl(clientUrl + "/" + clientFailUrl)
                .setCustomerEmail(userInfo.email)
                .setMode(SessionCreateParams.Mode.PAYMENT)
                .addAllLineItem(stripeLineItems)
                .build();

        Session session = Session.create(params);
        return new PspPaymentIntend(session.getUrl());
    }
}
