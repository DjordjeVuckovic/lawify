package org.lawify.psp.mediator;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {
        "org.lawify.psp.mediator",
        "org.lawify.psp.blocks.broker",
})
public class PspMediatorService {
    public static void main(String[] args) {
        SpringApplication.run(PspMediatorService.class,args);
    }

}
