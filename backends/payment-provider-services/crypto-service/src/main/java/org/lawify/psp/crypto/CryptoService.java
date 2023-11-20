package org.lawify.psp.crypto;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication(scanBasePackages = {
        "org.lawify.psp.crypto",
        "org.lawify.psp.blocks.broker",
})
@EnableTransactionManagement
public class CryptoService {
    public static void main(String[] args) {
        SpringApplication.run(CryptoService.class,args);
    }

}
