package org.lawify.psp.card.consumers;

import lombok.RequiredArgsConstructor;
import org.lawify.psp.contracts.requests.PaymentMessage;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
@RequiredArgsConstructor
public class BankClient {
    private final WebClient client;

    public void getTransactionResponse(PaymentMessage message){
        client.post()
                .uri("").bodyValue(message)
                .header("Content-Type", "application/json")
                .retrieve()
                .bodyToMono(String.class)
                .subscribe(response -> {
                    // Handle the response here
                    System.out.println("Response: " + response);
                });;
    }
}
