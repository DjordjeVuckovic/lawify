package org.lawify.psp.mediator.subscriptionServices.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;
@AllArgsConstructor
@NoArgsConstructor
@Data
public class SubscriptionPayment {
    String email;
    UUID subscriptionId;
}
