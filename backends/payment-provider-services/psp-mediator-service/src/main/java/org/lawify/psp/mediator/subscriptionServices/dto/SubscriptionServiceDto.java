package org.lawify.psp.mediator.subscriptionServices.dto;

import lombok.Builder;

import java.util.UUID;

@Builder
public class SubscriptionServiceDto {
    public UUID id;
    public String name;
}
