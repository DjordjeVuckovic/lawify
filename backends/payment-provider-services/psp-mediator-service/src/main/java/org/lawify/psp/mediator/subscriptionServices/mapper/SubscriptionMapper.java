package org.lawify.psp.mediator.subscriptionServices.mapper;

import org.lawify.psp.mediator.subscriptionServices.SubscriptionServiceEntity;
import org.lawify.psp.mediator.subscriptionServices.dto.SubscriptionServiceDto;

public class SubscriptionMapper {
    public static SubscriptionServiceDto toDto(SubscriptionServiceEntity service) {
        return SubscriptionServiceDto
                .builder()
                .id(service.getId())
                .name(service.getName())
                .imageUrl(service.getImageUrl())
                .build();
    }
}
