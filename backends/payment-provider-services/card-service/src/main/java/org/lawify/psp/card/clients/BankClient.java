package org.lawify.psp.card.clients;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.lawify.psp.contracts.requests.PaymentMessage;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class BankClient {
    @Value("${bank.baseUrl}")
    private String bankUrl;
    @Value("${bank.transactionPath}")
    private String transactionPath;
    private final WebClient client;

    public String getTransactionResponse(PaymentMessage message){
        var response = client.get()
                .uri(bankUrl+"/RequestTransaction?receiverId=" + message.merchantId + "&amount=" + message.amount)
                .header("Content-Type", "application/json")
                .retrieve()
                .bodyToMono(String.class)
                .block();
        assert response != null;
        var trimmed = response.replace("\"", "");
        log.info("Transaction id: {}",trimmed);
        return buildBankUrl(trimmed);
    }
    private String buildBankUrl(String id){
        return bankUrl + transactionPath + id;
    }
}
