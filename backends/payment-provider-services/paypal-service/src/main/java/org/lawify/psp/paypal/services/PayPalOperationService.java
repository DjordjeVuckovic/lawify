package org.lawify.psp.paypal.services;

import com.paypal.core.PayPalHttpClient;
import com.paypal.orders.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.lawify.psp.paypal.dtos.CompleteOrder;
import org.lawify.psp.paypal.dtos.PaymentOrder;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;
import java.util.NoSuchElementException;

@Slf4j
@Service
@RequiredArgsConstructor
public class PayPalOperationService {
    private final PayPalHttpClient payPalHttpClient;

    public PaymentOrder createOrder(BigDecimal fee,String email){
        var orderRequest = new OrderRequest();
        orderRequest.checkoutPaymentIntent("CAPTURE");
        var amountBreakDown = new AmountWithBreakdown()
                .currencyCode("USD")
                .value(fee.toString());
        var payee = new Payee().email(email);
        var purchaseUnitRequest = new PurchaseUnitRequest()
                .amountWithBreakdown(amountBreakDown)
                .payee(payee);
        orderRequest.purchaseUnits(List.of(purchaseUnitRequest));
       var applicationContext = new ApplicationContext()
               .returnUrl("http://localhost:5173/capture")
               .cancelUrl("http://localhost:4200/cancel");
       orderRequest.applicationContext(applicationContext);
       var ordersCreateRequest = new OrdersCreateRequest()
                .requestBody(orderRequest);

       try {
          var orderHttpResponse = payPalHttpClient.execute(ordersCreateRequest);
          var order = orderHttpResponse.result();
          var redirectUrl = order.links().stream()
                  .filter(link -> "approve".equals(link.rel()))
                  .findFirst()
                  .orElseThrow(NoSuchElementException::new)
                  .href();
          return new PaymentOrder("Success",order.id(),redirectUrl);
       }catch (IOException e) {
           log.error(e.getMessage());
           return new PaymentOrder("Error");
       }


    }

    public CompleteOrder completeOrder(String token) {
        var ordersCaptureRequest = new OrdersCaptureRequest(token);
        try {
            var httpResponse = payPalHttpClient.execute(ordersCaptureRequest);
            if(httpResponse.result().status()!=null){
                return new CompleteOrder("success");
            }
        } catch (IOException e){
            log.error(e.getMessage());
        }
        return new CompleteOrder("error");
    }
}
