package org.lawify.psp.mediator.subscriptionServices;

import lombok.RequiredArgsConstructor;
import org.lawify.psp.mediator.identity.merchants.MerchantRepository;
import org.lawify.psp.mediator.payments.IPaymentClient;
import org.lawify.psp.mediator.payments.PspPaymentIntend;
import org.lawify.psp.mediator.subscriptionServices.dto.SubscriptionPayment;
import org.lawify.psp.mediator.subscriptionServices.dto.SubscriptionServiceDto;
import org.lawify.psp.mediator.subscriptionServices.mapper.SubscriptionMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SubscriptionHandleService {
    private final SubscriptionServiceRepository  subscriptionServiceRepository;
    private final MerchantRepository merchantRepository;
    private final IPaymentClient paymentClient;

    public List<SubscriptionServiceDto> findAll(){
        return subscriptionServiceRepository.findAll()
                .stream()
                .map(SubscriptionMapper::toDto)
                .toList();
    }

    public List<SubscriptionServiceDto> findByUser(UUID userId){

        return merchantRepository.findSubscriptions(userId)
                .stream()
                .map(SubscriptionMapper::toDto)
                .toList();
    }

    public List<SubscriptionServiceDto> findAvailableByUser(UUID userId){
        var byUser = merchantRepository.findSubscriptions(userId)
                .stream()
                .map(SubscriptionMapper::toDto)
                .toList();

        var all = subscriptionServiceRepository.findAll()
                .stream()
                .map(SubscriptionMapper::toDto)
                .toList();

        return all.stream()
                .filter(subscription -> byUser.stream().noneMatch(x -> x.id == subscription.id))
                .collect(Collectors.toList());
    }

    public PspPaymentIntend handleNewPayment(SubscriptionPayment subscriptionPayment){
        return paymentClient.createPaymentIntent(10,subscriptionPayment.getEmail());
    }
}
