package org.lawify.psp.crypto.orders;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.lawify.psp.crypto.orders.dtos.CreateOrderRequest;
import org.lawify.psp.crypto.orders.dtos.CreateOrderResponse;
import org.lawify.psp.crypto.transactions.TransactionService;
import org.lawify.psp.crypto.transactions.dto.CreateTransactionRequest;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderService {
    private String sandboxUrl = "https://api-sandbox.coingate.com/v2/orders";
    private final RestTemplate restTemplate;
    private final TransactionService transactionService;

    public CreateOrderResponse createOrder(){
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        headers.set("Authorization", "Bearer " + "x9sgF8BY_9u-y2kRSVj-mg9fZ36-nsyjVxE7s7DV");
        var request = CreateOrderRequest.builder()
                .priceCurrency("USD")
                .callbackUrl("https://2b9f-188-2-101-162.ngrok-free.app/api/v1/transactions/notify")
                .receiveCurrency("BTC")
                .priceAmount(0.11)
                .build();
        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        map.add("price_amount",String.valueOf( request.getPriceAmount())); // Replace these with actual fields of your DTO
        map.add("price_currency", request.getPriceCurrency()); // Replace these with actual fields of your DTO
        map.add("receive_currency", request.getReceiveCurrency());
        map.add("order_id",UUID.randomUUID().toString());
        map.add("callback_url", request.getCallbackUrl());
        var response = restTemplate.exchange(sandboxUrl,
                HttpMethod.POST,
                new HttpEntity<>(map, headers),
                CreateOrderResponse.class);
        var statusCode = response.getStatusCode();
        var transaction = CreateTransactionRequest
                .builder()
                .transactionId(UUID.randomUUID())
                .orderId(response.getBody().getOrderId())
                .build();
        transactionService.saveTransaction(transaction);
        return response.getBody();

    }

}
