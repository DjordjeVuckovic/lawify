package org.lawify.psp.mediator.transactions;

import jakarta.persistence.*;
import lombok.*;
import org.lawify.psp.mediator.subscriptionServices.SubscriptionService;

import java.math.BigDecimal;
import java.util.Date;
import java.util.UUID;

@Entity
@Table(name = "payment_transactions")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PaymentTransaction {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    @Column
    private UUID orderId;
    @Column
    private Date timeStamp;
    @Column
    private UUID merchantId;
    @Column
    private BigDecimal amount;
    @Column
    private TransactionStatus status;
    @ManyToOne
    private SubscriptionService service;
    public String statusToString(){
         return switch (status){
            case STARTED -> "Started";
            case PENDING -> "Completed";
            case SUCCESS -> "Success";
            case FAILED -> "Fail";
        };
    }
}
