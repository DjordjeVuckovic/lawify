package org.lawify.psp.mediator.payments.stripe;

import com.stripe.param.checkout.SessionCreateParams;
import org.lawify.psp.mediator.payments.models.PspLineItem;

public class StripeLineItemBuilder {
    public static SessionCreateParams.LineItem buildStripeLineItem(PspLineItem pspLineItem) {
        return SessionCreateParams.LineItem.builder()
                .setQuantity(1L)
                .setPriceData(
                        SessionCreateParams.LineItem.PriceData.builder()
                        .setCurrency("EUR")
                        .setUnitAmount((long) (pspLineItem.price * 100))
                        .setProductData(
                                SessionCreateParams.LineItem.PriceData.ProductData.builder()
                                .setName(pspLineItem.name)
                                .setDescription("Product name: " + pspLineItem.name)
                                .putMetadata("id",pspLineItem.id.toString())
                                .build()
                        )
                        .build()
                )
                .build();
    }
}
