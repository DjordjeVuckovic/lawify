package org.lawify.psp.mediator.subscriptionServices;

import lombok.RequiredArgsConstructor;
import org.lawify.psp.mediator.identity.merchants.MerchantRepository;
import org.lawify.psp.mediator.payments.IPaymentClient;
import org.lawify.psp.mediator.payments.PspPaymentIntend;
import org.lawify.psp.mediator.payments.models.PspLineItem;
import org.lawify.psp.mediator.payments.models.UserInfo;
import org.lawify.psp.mediator.shared.exceptions.ApiNotFound;
import org.lawify.psp.mediator.subscriptionServices.dto.SubscriptionPayment;
import org.lawify.psp.mediator.subscriptionServices.dto.SubscriptionServiceDto;
import org.lawify.psp.mediator.subscriptionServices.mapper.SubscriptionMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SubscriptionHandleService {
    private final SubscriptionServiceRepository subscriptionServiceRepository;
    private final MerchantRepository merchantRepository;
    private final IPaymentClient paymentClient;

    public List<SubscriptionServiceDto> findAll() {
        return subscriptionServiceRepository.findAll()
                .stream()
                .map(SubscriptionMapper::toDto)
                .toList();
    }

    public List<SubscriptionServiceDto> findByUser(UUID userId) {

        return merchantRepository.findSubscriptions(userId)
                .stream()
                .map(SubscriptionMapper::toDto)
                .toList();
    }

    public List<SubscriptionServiceDto> findAvailableByUser(UUID userId) {
        var subsByUser = merchantRepository.findSubscriptions(userId)
                .stream()
                .map(SubscriptionMapper::toDto)
                .toList();

        var allSubs = subscriptionServiceRepository.findAll()
                .stream()
                .map(SubscriptionMapper::toDto)
                .toList();

        return allSubs.stream()
                .filter(sub -> subsByUser.stream().noneMatch(x -> x.id.equals(sub.id)))
                .toList();

    }

    public PspPaymentIntend handleNewPayment(SubscriptionPayment subscriptionPayment) {
        var user = merchantRepository.findById(subscriptionPayment.getUserId())
                .orElseThrow(() -> new ApiNotFound("User not found"));

        var userInfo = UserInfo.builder()
                .email(user.getEmail())
                .id(user.getId())
                .build();

        var allItems = subscriptionPayment.getSubscriptionIds()
                .stream()
                .map(x -> subscriptionServiceRepository
                        .findById(x)
                        .orElseThrow(() -> new ApiNotFound("Item with id: " + x + "not found"))
                ).map(x -> PspLineItem
                        .builder()
                        .name(x.getName())
                        .id(x.getId())
                        .price(x.getPrice())
                        .build()
                ).toList();

        return paymentClient.createPaymentIntent(userInfo, allItems);
    }

    public void updateUserSubscriptions(String userEmail, List<UUID> subIds) {
        var payedSubs = subIds
                .stream()
                .map(x -> subscriptionServiceRepository
                        .findById(x)
                        .orElseThrow(() -> new ApiNotFound("Item with id: " + x + "not found"))
                ).toList();

        var user = merchantRepository.findByEmail(userEmail).orElseThrow(() -> new ApiNotFound("User not found"));

        user.setSubscriptionServices(payedSubs);
        merchantRepository.save(user);



    }
}
