package org.lawify.psp.qr;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {
        "org.lawify.psp.qr",
        "org.lawify.psp.blocks.broker",
})
public class QrCodeService {
    public static void main(String[] args) {
        SpringApplication.run(QrCodeService.class,args);
    }

}
