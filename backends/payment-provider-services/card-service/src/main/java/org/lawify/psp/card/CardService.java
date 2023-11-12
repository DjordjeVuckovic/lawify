package org.lawify.psp.card;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {
        "org.lawify.psp.card",
        "org.lawify.psp.blocks.broker",
})
public class CardService {
    public static void main(String[] args) {
        SpringApplication.run(CardService.class,args);
    }

}
