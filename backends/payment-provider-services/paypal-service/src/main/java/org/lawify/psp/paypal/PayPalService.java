package org.lawify.psp.paypal;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {
        "org.lawify.psp.paypal",
        "org.lawify.psp.blocks.broker",
})
public class PayPalService {
    public static void main(String[] args) {
        SpringApplication.run(PayPalService.class,args);
    }

}
