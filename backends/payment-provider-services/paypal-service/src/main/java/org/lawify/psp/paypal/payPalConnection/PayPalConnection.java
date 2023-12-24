package org.lawify.psp.paypal.payPalConnection;

import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Entity
@Table(name = "paypal_connection")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PayPalConnection {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private UUID UserId;
    private String PayPalEmail;
}
