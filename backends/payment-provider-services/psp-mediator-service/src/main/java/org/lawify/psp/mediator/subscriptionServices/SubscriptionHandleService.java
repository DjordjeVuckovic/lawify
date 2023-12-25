package org.lawify.psp.mediator.subscriptionServices;

import lombok.RequiredArgsConstructor;
import org.lawify.psp.mediator.identity.merchants.MerchantRepository;
import org.lawify.psp.mediator.subscriptionServices.dto.SubscriptionServiceDto;
import org.lawify.psp.mediator.subscriptionServices.mapper.SubscriptionMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class SubscriptionHandleService {
    private final SubscriptionServiceRepository  subscriptionServiceRepository;
    private final MerchantRepository merchantRepository;

    public List<SubscriptionServiceDto> findAll(){
        return subscriptionServiceRepository.findAll()
                .stream()
                .map(SubscriptionMapper::toDto)
                .toList();
    }

    public List<SubscriptionServiceDto> findByMerchant(UUID merchantId){

        return subscriptionServiceRepository.findAll()
                .stream()
                .map(SubscriptionMapper::toDto)
                .toList();
    }
}
