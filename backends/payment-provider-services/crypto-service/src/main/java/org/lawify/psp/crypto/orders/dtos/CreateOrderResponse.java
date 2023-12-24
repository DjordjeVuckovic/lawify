package org.lawify.psp.crypto.orders.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CreateOrderResponse {
    @JsonProperty("order_id")
    private String orderId;
    @JsonProperty("price_amount")
    private Double priceAmount;
    @JsonProperty("price_currency")
    private String priceCurrency;
    @JsonProperty("receive_currency")
    private String receiveCurrency;
    @JsonProperty("payment_url")
    private String paymentUrl;
}
