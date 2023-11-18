package org.lawify.psp.mediator;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication(scanBasePackages = {
        "org.lawify.psp.mediator",
        "org.lawify.psp.blocks.broker",
})
@EnableTransactionManagement
public class PspMediatorService {
    public static void main(String[] args) {
        SpringApplication.run(PspMediatorService.class,args);
    }

}
