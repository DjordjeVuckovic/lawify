package org.lawify.psp.mediator.merchants;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.lawify.psp.mediator.apiKeys.ApiKey;
import org.lawify.psp.mediator.crypto.converters.CryptoConverter;
import org.lawify.psp.mediator.subscriptionServices.SubscriptionService;

import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "merchants", indexes = {
        @Index(name= "merchant_name_index", columnList = "name")
})
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Merchant {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    @OneToMany(
            fetch = FetchType.EAGER,
            mappedBy = "merchant", cascade = CascadeType.ALL
    )
    @OnDelete(action = OnDeleteAction.CASCADE)
    private List<ApiKey> apiKeys;
    @ManyToMany(
            fetch = FetchType.LAZY
    )
    private List<SubscriptionService> subscriptionServices;
    @Column
    private String name;
    @Column
    @Convert(converter = CryptoConverter.class)
    private String bankAccount;

}
