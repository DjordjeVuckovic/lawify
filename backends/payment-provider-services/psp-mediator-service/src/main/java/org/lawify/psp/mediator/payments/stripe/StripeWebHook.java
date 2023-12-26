package org.lawify.psp.mediator.payments.stripe;

import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.LineItemCollection;
import com.stripe.model.StripeObject;
import com.stripe.model.checkout.Session;
import com.stripe.net.Webhook;
import com.stripe.param.checkout.SessionListLineItemsParams;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.lawify.psp.mediator.subscriptionServices.SubscriptionHandleService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("psp-mediator-service/webhook")
@Slf4j
@RequiredArgsConstructor
public class StripeWebHook {
    @Value("${stripe.key}")
    private String stripeApiKey;
    @Value("${stripe.endpoint-secret}")
    private String endpointSecret;
    private final SubscriptionHandleService subscriptionHandleService;

    @SneakyThrows
    @PostMapping
    public ResponseEntity<String> handleStripeWebhook(
            @RequestBody String payload,
            @RequestHeader("Stripe-Signature") String sigHeader) {
        Stripe.apiKey = stripeApiKey;
        log.info("Sig header {}", sigHeader);

        // Verify and process the webhook
        var event = Webhook.constructEvent(
                payload, sigHeader, endpointSecret
        );

        StripeObject stripeObject = event.getDataObjectDeserializer()
                .getObject().orElse(null);
        if (stripeObject == null) {
            log.error("Failed to deserialize stripe response hook");
        }

        if ("checkout.session.completed".equals(event.getType())) {
            Session session = (Session) stripeObject;
            if (session != null) {
                log.info("Checkout completed with response {}", session);
                String customerEmail = session.getCustomerEmail();
                log.info("Customer mail {}", customerEmail);

                // extract line items
                var subs = getLineItems(session.getId());
                subscriptionHandleService.updateUserSubscriptions(customerEmail,subs);
            }
        }


        return ResponseEntity.ok().build();
    }

    private List<UUID> getLineItems(String sessionId) throws StripeException {
        // Fetch the session again to get the line items
        Session retrievedSession = Session.retrieve(sessionId);
        LineItemCollection lineItems = retrievedSession
                .listLineItems(
                        SessionListLineItemsParams.builder()
                                .addExpand("data.price.product")
                                .build()
                );
        var subIds = new ArrayList<UUID>();
        lineItems.getData().forEach(item -> {
            log.info("Line Item Id: {}", item.getId());
            String productName = item.getDescription();
            Map<String, String> metadata = item.getPrice().getProductObject().getMetadata();
            log.info("Metadata: {}", metadata);

            // Extract metadata like 'id'
            String itemId = metadata.get("id");
            log.info("Product Name: {}, Item ID: {}", productName, itemId);
            subIds.add(UUID.fromString(itemId));
        });
        return subIds;
    }
}
