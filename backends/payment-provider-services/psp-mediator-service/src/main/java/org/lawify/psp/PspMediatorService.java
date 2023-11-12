package org.lawify.psp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"org.lawify.psp.broker"})
public class PspMediatorService {
    public static void main(String[] args) {
        SpringApplication.run(PspMediatorService.class,args);
    }

}
