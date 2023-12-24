package org.lawify.psp.crypto.accounts;

import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;
import org.lawify.psp.crypto.shared.crypto.converters.CryptoConverter;

import java.util.UUID;
@Entity
@Table(name = "accounts")
@Getter
@Setter
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class Account {
    @Id
    private UUID merchantId;
    @Convert(converter = CryptoConverter.class)
    private String apiKey;
}
