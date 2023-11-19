package org.lawify.psp.mediator.subscriptionServices;

import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Entity
@Table(name = "subscription_services", indexes = {
        @Index(name= "sub_name_index", columnList = "name"),
        @Index(name= "queue_name_index", columnList = "queueName"),
})
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SubscriptionService {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    @Column
    private String name;
    @Column
    private String queueName;
    @Column
    private String imageUrl;
}
