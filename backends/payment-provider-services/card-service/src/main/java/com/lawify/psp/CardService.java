package com.lawify.psp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"com.lawify.psp", "org.lawify.psp.broker"})
public class CardService {
    public static void main(String[] args) {
        SpringApplication.run(CardService.class,args);
    }

}
