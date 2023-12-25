package org.lawify.psp.mediator.identity.merchants;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.lawify.psp.mediator.apiKeys.ApiKey;
import org.lawify.psp.mediator.shared.crypto.converters.CryptoConverter;
import org.lawify.psp.mediator.identity.PspRole;
import org.lawify.psp.mediator.identity.UserBase;
import org.lawify.psp.mediator.subscriptionServices.SubscriptionServiceEntity;

import java.util.List;

@Entity
@Table(name = "merchants", indexes = {
        @Index(name= "merchant_name_index", columnList = "name"),
        @Index(name = "merchant_email_index", columnList = "email")
})
@Getter
@Setter
@NoArgsConstructor
public class Merchant extends UserBase {
    @OneToMany(
            fetch = FetchType.EAGER,
            mappedBy = "merchant", cascade = CascadeType.ALL
    )
    @OnDelete(action = OnDeleteAction.CASCADE)
    private List<ApiKey> apiKeys;
    @ManyToMany(
            fetch = FetchType.LAZY
    )
    private List<SubscriptionServiceEntity> subscriptionServices;
    @Column
    private String name;
    @Column
    @Convert(converter = CryptoConverter.class)
    private String bankAccount;

    public Merchant(String name, String bankAccount,String email, String password) {
        super();
        this.email = email;
        this.password = password;
        this.name = name;
        this.bankAccount = bankAccount;
        addRole(PspRole.MERCHANT);
    }
}
