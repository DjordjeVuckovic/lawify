package org.lawify.psp.paypal.payPalConnection;

import lombok.RequiredArgsConstructor;
import org.lawify.psp.paypal.shared.exceptions.ApiNotFound;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PayPalConnectionService {
    private final PayPalConnectionRepository payPalConnectionRepository;
    public PayPalConnection getPayPalConnectionByUserId(UUID userId){
        var paypalConnection = payPalConnectionRepository.findByUserId(userId)
                .orElseThrow(()-> new ApiNotFound("Seller doesn't have paypal account"));
        return paypalConnection;
    }
}
