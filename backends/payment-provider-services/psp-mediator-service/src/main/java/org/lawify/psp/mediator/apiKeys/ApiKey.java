package org.lawify.psp.mediator.apiKeys;

import jakarta.persistence.*;
import lombok.*;
import org.lawify.psp.mediator.merchants.Merchant;

import java.util.Date;
import java.util.UUID;

@Entity
@Table(name = "api_keys",indexes = {
        @Index(name= "key_index", columnList = "key")
})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ApiKey {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    @Column
    private String key;
    @ManyToOne
    Merchant merchant;
    @Column
    private Date createdAt;
    @Column
    private Date expiredAt;

    public boolean IsExpired() {
        return expiredAt.after(new Date());
    }
}
